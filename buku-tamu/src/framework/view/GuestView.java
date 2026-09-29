package framework.view;

import adapter.presenter.GuestPresenter;
import framework.util.InputUtil;
import usecase.GuestUseCase;

public class GuestView {
    private final GuestUseCase guestUseCase;

    private final GuestPresenter presenter;

    public GuestView(GuestUseCase guestUseCase, GuestPresenter presenter) {
        this.guestUseCase = guestUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar guest terkini sebelum menu
            presenter.showGuests(guestUseCase.getAllGuests());
            printMenu();

            String input = InputUtil.input("Pilih");
            if (input.isBlank()) {
                break;
            }

            switch (input) {
                case "1" -> addGuest();
                case "2" -> searchGuest();
                case "3" -> removeGuest();
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
        System.out.println("1. Daftarkan");
        System.out.println("2. Cari");
        System.out.println("3. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah guest baru. */
    private void addGuest() {
        System.out.println("[Mendaftarkan Tamu]");
        String name = InputUtil.input("Nama (x Jika Batal)");

        if (name.isBlank() || name.equals("x")) {
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (purpose.isBlank() || purpose.equals("x")) {
            return;
        }

        presenter.showAddSuccess(guestUseCase.addGuest(name, purpose));
    }

    /** Form hapus guest berdasarkan ID. */
    private void removeGuest() {
        System.out.println("[Menghapus Tamu]");
        String strIdGuest = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");

        if (strIdGuest.isBlank() || strIdGuest.equals("x")) {
            return;
        }

        Integer id = parseId(strIdGuest);
        if (id == null) {
            return;
        }

        if (guestUseCase.removeGuest(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Form cari guest berdasarkan kata kunci. */
    private void searchGuest() {
        System.out.println("[Mencari Tamu]");
        String keyword = InputUtil.input("Nama (x Jika Batal)");

        if (!keyword.isBlank() && !keyword.equals("x")) {
            presenter.showSearchResults(guestUseCase.searchGuests(keyword), keyword);
        }
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
}
