package com.tenniscourts.guests;

import java.util.List;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestDTO addGuest(CreateGuestDTO createGuestDTO) {
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(createGuestDTO)));
    }

    public List<GuestDTO> findAllGuests() {
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDTO findGuestById(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByNameContainingIgnoreCase(name));
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        Guest guest = guestMapper.map(guestDTO);
        return guestMapper.map(guestRepository.save(guest));
    }

    public void deleteGuest(Long guestId) {
        Guest guest =  guestMapper.map(findGuestById(guestId));
        guestRepository.delete(guest);
    }

    // private void validateCancellation(Guest guest) {
    //     if (!GuestStatus.READY_TO_PLAY.equals(guest.getGuestStatus())) {
    //         throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
    //     }

    //     if (guest.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
    //         throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
    //     }
    // }
}
