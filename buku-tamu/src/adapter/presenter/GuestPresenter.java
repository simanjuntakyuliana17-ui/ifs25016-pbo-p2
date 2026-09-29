package adapter.presenter;

import domain.entity.Guest;
import java.util.List;

public class GuestPresenter {

    private String format(Guest guest) {
        // <id> | <nama> | <tujuan kunjungan>
        return String.format("%d | %s | %s", guest.getId(), guest.getName(), guest.getPurpose());
    }

    private void printList(List<Guest> guests, String header, String emptyMessage) {
        System.out.println(header);

        if (guests.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Guest guest : guests) {
            System.out.println(format(guest));
        }
    }

    public void showGuests(List<Guest> guests) {
        printList(guests, "Daftar Tamu:", "- Data tamu belum tersedia!");
    }

    public void showSearchResults(List<Guest> guests, String keyword) {
        printList(guests, "Hasil Pencarian: \"" + keyword + "\"", "- Tamu tidak ditemukan!");
    }

    public void showSortedGuests(List<Guest> guests) {
        printList(guests, "Daftar Tamu (Terurut):", "- Data tamu belum tersedia!");
    }

    public void showAddSuccess(Guest guest) {
        System.out.printf("Berhasil mendaftarkan tamu: %s%n", format(guest));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus tamu.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus tamu dengan ID: %d.%n", id);
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah tamu.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah tamu dengan ID: %d.%n", id);
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