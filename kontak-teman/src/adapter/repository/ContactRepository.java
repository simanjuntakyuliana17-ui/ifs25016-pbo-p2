package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactRepository implements IContactRepository {
    private final List<Contact> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Contact> findById(int id) {
        return data.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();
    }

    @Override
    public Contact save(String name, String phone, String email) {
        Contact contact = new Contact(nextId(), name, phone, email);
        data.add(contact);
        return contact;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(contact -> contact.getId() == id);
    }

    @Override
    public void update(Contact contact) {
        // Entity bersifat mutable dan disimpan by-reference, sehingga perubahan
        // pada instance sudah otomatis tercermin di penyimpanan in-memory.
        // Method ini tetap ada agar kontrak port valid untuk implementasi lain.
    }

    private int nextId() {
        return ++idCounter;
    }
}