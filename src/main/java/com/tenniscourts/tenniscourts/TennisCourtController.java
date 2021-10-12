package com.tenniscourts.tenniscourts;

import java.util.List;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.tenniscourts.requests.CreateTennisCourtRequestDTO;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("tennis-court")
public class TennisCourtController extends BaseRestController {

    private final TennisCourtService tennisCourtService;

    @ApiOperation(value = "List all tennis courts.")
    @GetMapping
    public ResponseEntity<List<TennisCourtDTO>> listAll() {
        return ResponseEntity.ok(tennisCourtService.findAllTennisCourt());
    }

    @ApiOperation(value = "Register a tennis court.")
    @PostMapping
    public ResponseEntity<Void> addTennisCourt(CreateTennisCourtRequestDTO createTennisCourtDTO) {
        TennisCourtDTO newTennisCourt = TennisCourtDTO.builder().name(createTennisCourtDTO.getName()).build();
        return ResponseEntity.created(locationByEntity(tennisCourtService.addTennisCourt(newTennisCourt).getId())).build();
    }

    @ApiOperation(value = "Find a tennis court using the ID.")
    @GetMapping("/{tennisCourtId}")
    public ResponseEntity<TennisCourtDTO> findTennisCourtById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtById(tennisCourtId));
    }

    @ApiOperation(value = "Find a tennis court using the ID with all schedules.")
    @GetMapping("/{tennisCourtId}/schedules")
    public ResponseEntity<TennisCourtDTO> findTennisCourtWithSchedulesById(@PathVariable Long tennisCourtId) {
        return ResponseEntity.ok(tennisCourtService.findTennisCourtWithSchedulesById(tennisCourtId));
    }
}
