package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository implements IGuestRepository {
    private final List<Guest> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Guest> findAll() {
        List<Guest> copies = new ArrayList<>();
        for (Guest guest : data) {
            copies.add(copyOf(guest));
        }

        return copies;
    }

    @Override
    public Guest save(String name, String purpose) {
        Guest guest = new Guest(nextId(), name, purpose);
        data.add(guest);
        return copyOf(guest);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(guest -> guest.getId() == id);
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Guest copyOf(Guest guest) {
        return new Guest(guest.getId(), guest.getName(), guest.getPurpose());
    }

    private int nextId() {
        return ++idCounter;
    }
}
