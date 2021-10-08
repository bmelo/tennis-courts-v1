package com.tenniscourts.schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@Setter
public class FindScheduleRequestDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate endDate;

}
