package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        //TODO: implement addSchedule
        return null;
    }

    public List<ScheduleDTO> findSchedulesByDates(FindScheduleRequestDTO findScheduleRequestDTO) {
        //TODO: implement addSchedule
        LocalDateTime startDate = LocalDateTime.of(findScheduleRequestDTO.getStartDate(), LocalTime.of(0, 0));
        LocalDateTime endDate = LocalDateTime.of(findScheduleRequestDTO.getEndDate(), LocalTime.of(23, 59));
        return null;
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        //TODO: implement
        return null;
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
