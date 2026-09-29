package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GuestRepository implements IGuestRepository {
    private final List<Guest> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Guest> findById(int id) {
        return data.stream()
                .filter(guest -> guest.getId() == id)
                .findFirst();
    }

    @Override
    public Guest save(String name, String purpose) {
        Guest guest = new Guest(nextId(), name, purpose);
        data.add(guest);
        return guest;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(guest -> guest.getId() == id);
    }

    @Override
    public void update(Guest guest) {
        // Entity bersifat mutable dan disimpan by-reference, sehingga perubahan
        // pada instance sudah otomatis tercermin di penyimpanan in-memory.
        // Method ini tetap ada agar kontrak port valid untuk implementasi lain.
    }

    private int nextId() {
        return ++idCounter;
    }
}