package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.guests.Guest;
import com.tenniscourts.guests.GuestRepository;
import com.tenniscourts.reservations.requests.CreateReservationRequestDTO;
import com.tenniscourts.reservations.requests.RescheduleRequestDTO;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final ScheduleRepository scheduleRepository;

    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Guest guest = guestRepository.findById(createReservationRequestDTO.getGuestId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
        Schedule schedule = scheduleRepository.findById(createReservationRequestDTO.getScheduleId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Schedule not found.");
        });
        Reservation reservation = Reservation.builder()
                                    .guest(guest)
                                    .schedule(schedule)
                                    .value(BigDecimal.valueOf(10))
                                    .reservationStatus(ReservationStatus.READY_TO_PLAY)
                                    .build();
                                    
        return reservationMapper.map(reservationRepository.saveAndFlush(reservation));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO noShow(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {
            this.validateNoShow(reservation);
            return reservationMapper.map(this.updateReservation(reservation, BigDecimal.valueOf(0), ReservationStatus.NOT_SHOW_UP));
        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    private void validateNoShow(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot set as NOT-SHOW-UP because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getEndDateTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only past dates.");
        }
    }


    public BigDecimal getRefundValue(Reservation reservation) {
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        if (hours >= 24) {
            return reservation.getValue();
        } else if (hours >= 12) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.75));
        } else if (hours >= 2) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.5));
        } else if (hours >= 0) {
            return reservation.getValue().multiply(BigDecimal.valueOf(0.25));
        }

        return BigDecimal.ZERO;
    }

    public ReservationDTO rescheduleReservation(RescheduleRequestDTO rescheduleFormDTO) {
        ReservationDTO reservationDTO = findReservation(rescheduleFormDTO.getReservationId());
        Long previousScheduleId = reservationDTO.getSchedule().getId();
        Long scheduleId = rescheduleFormDTO.getScheduleId();
        
        System.out.println(scheduleId);
        System.out.println(previousScheduleId);

        if (scheduleId.equals(previousScheduleId)) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        
        Reservation previousReservation = cancel(reservationDTO.getId());
        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
