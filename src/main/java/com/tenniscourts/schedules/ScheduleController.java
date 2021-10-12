package com.tenniscourts.schedules;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.schedules.requests.CreateScheduleRequestDTO;
import com.tenniscourts.schedules.requests.FindScheduleRequestDTO;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("schedule")
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;

    @ApiOperation(value = "Create a new schedule slot for a tennis court.")
    @PostMapping
    public ResponseEntity<ScheduleDTO> addScheduleTennisCourt(CreateScheduleRequestDTO createScheduleRequestDTO) {
        return ResponseEntity
                .created(locationByEntity(scheduleService.addSchedule(createScheduleRequestDTO).getId()))
                .build();
    }

    @ApiOperation(value = "Find all schedules using a range of dates.")
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(FindScheduleRequestDTO findScheduleRequestDTO) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(findScheduleRequestDTO));
    }

    @ApiOperation(value = "Find a schedule using the ID.")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTO> findByScheduleId(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
