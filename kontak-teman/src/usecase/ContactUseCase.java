package usecase;

import domain.entity.SortOption;
import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.List;
import java.util.Optional;

public class ContactUseCase {
    private final IContactRepository contactRepository;

    public ContactUseCase(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact addContact(String name, String phone, String email) {
        return contactRepository.save(name, phone, email);
    }

    public boolean removeContact(int id) {
        return contactRepository.deleteById(id);
    }

    public boolean updateContact(int id, String name, String phone, String email) {
        Optional<Contact> found = contactRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Contact contact = found.get();
        if (name != null) {
            contact.setName(name);
        }

        if (phone != null) {
            contact.setPhone(phone);
        }

        if (email != null) {
            contact.setEmail(email);
        }

        contactRepository.update(contact);
        return true;
    }

    public List<Contact> searchContacts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public List<Contact> sortContacts(SortOption option) {
        return contactRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}