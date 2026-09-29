package adapter.presenter;

import domain.entity.Activity;
import java.util.List;

public class ActivityPresenter {

    private String format(Activity activity) {
        return String.format("%d | %s | %s | %s", activity.getId(), activity.getTitle(), activity.getDay(), activity.getTime());
    }


    private void printList(List<Activity> activitys, String header, String emptyMessage) {
        System.out.println(header);

        if (activitys.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Activity activity : activitys) {
            System.out.println(format(activity));
        }
    }

    public void showActivitys(List<Activity> activitys) {
        printList(activitys, "Daftar Kegiatan:", "- Data kegiatan belum tersedia!");
    }

    public void showSearchResults(List<Activity> activitys, String keyword) {
        printList(activitys, "Hasil Pencarian: \"" + keyword + "\"", "- Kegiatan tidak ditemukan!");
    }

    public void showSortedActivitys(List<Activity> activitys) {
        printList(activitys, "Daftar Kegiatan (Terurut):", "- Data kegiatan belum tersedia!");
    }

    public void showAddSuccess(Activity activity) {
        System.out.printf("Berhasil menambah kegiatan: %s%n", format(activity));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kegiatan.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kegiatan dengan ID: %d.%n", id);
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kegiatan.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kegiatan dengan ID: %d.%n", id);
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