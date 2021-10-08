package com.tenniscourts.guests;

import java.util.List;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@AllArgsConstructor
@RestController
@RequestMapping("guest")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @ApiOperation(value = "List all guests.")
    @GetMapping("/all")
    public ResponseEntity<List<GuestDTO>> listAll() {
        return ResponseEntity.ok(guestService.findAllGuests());
    }

    @ApiOperation(value = "Find a guest using the ID.")
    @GetMapping("/{guestId}")
    public ResponseEntity<GuestDTO> findById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @ApiOperation(value = "Find guests by name using the query param 'search'.")
    @GetMapping
    public ResponseEntity<List<GuestDTO>> findByName(
            @RequestParam(value = "search", defaultValue = "name", required = true) String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @ApiOperation(value = "Add a guest.")
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody CreateGuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @ApiOperation(value = "Update a guest.")
    @PutMapping
    public ResponseEntity<GuestDTO> update(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @ApiOperation(value = "Delete a guest using the ID.")
    @DeleteMapping("/{guestId}")
    public ResponseEntity<String> delete(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.ok("Guest deleted.");
    }
}
