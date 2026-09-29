package usecase;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.List;
import java.util.Optional;

public class GuestUseCase {
    private final IGuestRepository guestRepository;

    public GuestUseCase(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Guest addGuest(String name, String purpose) {
        return guestRepository.save(name, purpose);
    }

    public boolean removeGuest(int id) {
        return guestRepository.deleteById(id);
    }

    public boolean updateGuest(int id, String name, String purpose) {
        Optional<Guest> found = guestRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Guest guest = found.get();
        if (name != null) {
            guest.setName(name);
        }

        if (purpose != null) {
            guest.setPurpose(purpose);
        }

        guestRepository.update(guest);
        return true;
    }

    public List<Guest> searchGuests(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return guestRepository.findAll().stream()
                .filter(guest -> guest.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }
}