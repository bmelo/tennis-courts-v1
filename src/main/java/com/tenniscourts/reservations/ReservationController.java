package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.reservations.requests.CreateReservationRequestDTO;
import com.tenniscourts.reservations.requests.RescheduleRequestDTO;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @ApiOperation(value = "Book a reservation for a guest.")
    @PostMapping
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @ApiOperation(value = "Find a reservation using its ID.")
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @ApiOperation(value = "Mark a reservation as NOT-SHOW-UP.")
    @PutMapping("/no-show/{reservationId}")
    public ResponseEntity<ReservationDTO> notShowUp(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.noShow(reservationId));
    }

    @ApiOperation(value = "Cancel a reservation using the ID.")
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @ApiOperation(value = "Change the schedule of a reservation.")
    @PutMapping
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestBody RescheduleRequestDTO rescheduleFormDTO) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(rescheduleFormDTO));
    }
}
