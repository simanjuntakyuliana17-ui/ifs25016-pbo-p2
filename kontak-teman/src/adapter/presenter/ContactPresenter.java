package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter {

    private String format(Contact contact) {
        // <id> | <nama> | <telepon> | <email>
        return String.format("%d | %s | %s | %s", contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }

    private void printList(List<Contact> contacts, String header, String emptyMessage) {
        System.out.println(header);

        if (contacts.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Contact contact : contacts) {
            System.out.println(format(contact));
        }
    }

    public void showContacts(List<Contact> contacts) {
        printList(contacts, "Daftar Kontak:", "- Data kontak belum tersedia!");
    }

    public void showSearchResults(List<Contact> contacts, String keyword) {
        printList(contacts, "Hasil Pencarian: \"" + keyword + "\"", "- Kontak tidak ditemukan!");
    }

    public void showSortedContacts(List<Contact> contacts) {
        printList(contacts, "Daftar Kontak (Terurut):", "- Data kontak belum tersedia!");
    }

    public void showAddSuccess(Contact contact) {
        System.out.printf("Berhasil menambah kontak: %s%n", format(contact));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kontak.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kontak dengan ID: %d.%n", id);
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kontak.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kontak dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showInvalidFinishedStatus() {
        System.out.println("[!] Pilihan status selesai tidak valid (gunakan y/n)!");
    }
}