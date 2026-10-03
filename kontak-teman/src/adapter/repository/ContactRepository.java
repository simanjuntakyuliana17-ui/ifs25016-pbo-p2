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
        List<Contact> copies = new ArrayList<>();
        for (Contact contact : data) {
            copies.add(copyOf(contact));
        }

        return copies;
    }

    @Override
    public Optional<Contact> findById(int id) {
        return data.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .map(this::copyOf);
    }

    @Override
    public Contact save(String name, String phone, String email) {
        Contact contact = new Contact(nextId(), name, phone, email);
        data.add(contact);
        return copyOf(contact);
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(contact -> contact.getId() == id);
    }

    @Override
    public void update(Contact contact) {
        // findById/findAll mengembalikan salinan, jadi perubahan baru tersimpan
        // setelah objek yang tersimpan diganti dengan salinan dari parameter.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == contact.getId()) {
                data.set(i, copyOf(contact));
                return;
            }
        }
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Contact copyOf(Contact contact) {
        return new Contact(contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }

    private int nextId() {
        return ++idCounter;
    }
}
