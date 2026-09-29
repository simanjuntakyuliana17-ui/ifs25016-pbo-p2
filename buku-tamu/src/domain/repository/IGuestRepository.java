package domain.repository;

import domain.entity.Guest;
import java.util.List;
import java.util.Optional;

public interface IGuestRepository {
    List<Guest> findAll();

    Optional<Guest> findById(int id);

    Guest save(String name, String purpose);

    boolean deleteById(int id);

    void update(Guest contact);
}