package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ContactUseCase;

public class ContactView {
    private final ContactUseCase contactUseCase;

    private final ContactPresenter presenter;

    public ContactView(ContactUseCase contactUseCase, ContactPresenter presenter) {
        this.contactUseCase = contactUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar contact terkini sebelum menu
            presenter.showContacts(contactUseCase.getAllContacts());
            printMenu();

            String input = InputUtil.input("Pilih");
            if (input.isBlank()) {
                break;
            }

            switch (input) {
                case "1" -> addContact();
                case "2" -> updateContact();
                case "3" -> searchContact();
                case "4" -> sortContact();
                case "5" -> removeContact();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah contact baru. */
    private void addContact() {
        System.out.println("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");

        if (name.isBlank() || name.equals("x")) {
            return;
        }

        String phone = InputUtil.input("Telepon (x Jika Batal)");
        if (phone.isBlank() || phone.equals("x")) {
            return;
        }

        String email = InputUtil.input("Email (x Jika Batal)");
        if (email.isBlank() || email.equals("x")) {
            return;
        }

        presenter.showAddSuccess(contactUseCase.addContact(name, phone, email));
    }

    /** Form hapus contact berdasarkan ID. */
    private void removeContact() {
        System.out.println("[Menghapus Kontak]");
        String strIdContact = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");

        if (strIdContact.isBlank() || strIdContact.equals("x")) {
            return;
        }

        Integer id = parseId(strIdContact);
        if (id == null) {
            return;
        }

        if (contactUseCase.removeContact(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Form ubah judul dan/atau status contact. */
    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        String strIdContact = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");

        if (strIdContact.isBlank() || strIdContact.equals("x")) {
            return;
        }

        Integer id = parseId(strIdContact);
        if (id == null) {
            return;
        }

        String newName = InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)");
        String newPhone = InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)");
        String newEmail = InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)");

        // null berarti field tersebut tidak diubah
        String name = newName.isBlank() ? null : newName;
        String phone = newPhone.isBlank() ? null : newPhone;
        String email = newEmail.isBlank() ? null : newEmail;

        if (contactUseCase.updateContact(id, name, phone, email)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    /** Form cari contact berdasarkan kata kunci. */
    private void searchContact() {
        System.out.println("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!keyword.isBlank() && !keyword.equals("x")) {
            presenter.showSearchResults(contactUseCase.searchContacts(keyword), keyword);
        }
    }

    /** Form urutkan contact berdasarkan pilihan user. */
    private void sortContact() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        if (input.isBlank() || input.equals("x")) {
            return;
        }

        // Konversi input angka ke enum domain
        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedContacts(contactUseCase.sortContacts(option));
    }

    /**
     * Mengonversi input string menjadi ID.
     *
     * @return ID jika valid, null jika tidak valid (error sudah ditampilkan)
     */
    private Integer parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            presenter.showInvalidId();
            return null;
        }
    }

    /** Memetakan pilihan menu (1-2) ke {@link SortOption} domain. */
    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            default -> null;
        };
    }
}
