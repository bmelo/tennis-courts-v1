package com.tenniscourts.reservations;

import java.time.LocalDateTime;

import com.tenniscourts.guests.Guest;
import com.tenniscourts.schedules.Schedule;

public class ReservationServiceFixture {
    public static Guest guest() {
        return Guest.builder().name("Bruno Melo").build();
    }

    public static Schedule schedule(LocalDateTime startDateTime) {
        return Schedule.builder()
            .startDateTime(startDateTime)
            .endDateTime(startDateTime.plusHours(1))
            .build();
    }
}
