package com.tenniscourts.schedules.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Getter
@Setter
public class FindScheduleRequestDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, example = "2021-10-01")
    @NotNull
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, example = "2021-10-20")
    @NotNull
    private LocalDate endDate;

}
