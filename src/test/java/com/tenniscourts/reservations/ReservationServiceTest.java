package com.tenniscourts.reservations;

import com.tenniscourts.guests.GuestMapper;
import com.tenniscourts.guests.GuestService;
import com.tenniscourts.schedules.Schedule;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ReservationService.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    private GuestService guestService;

    @Mock
    private GuestMapper guestMapper;

    @Mock
    private ReservationRepository repository;

    @Mock
    private ReservationMapper mapper;

    @Test
    public void getRefundValueFullRefund() {
        Schedule schedule = ReservationServiceFixture.schedule(LocalDateTime.now().plusDays(2));
        Reservation reservation = Reservation.builder().schedule(schedule).value(new BigDecimal(10L)).build();

        Assert.assertEquals(reservationService.getRefundValue(reservation), new BigDecimal(10));
    }

    @Test
    public void bookReservation() {
        // TODO: Finish the tests
    }
}