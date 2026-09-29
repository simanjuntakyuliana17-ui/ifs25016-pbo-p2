package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

public class ActivityView {
    private final ActivityUseCase activityUseCase;

    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase activityUseCase, ActivityPresenter presenter) {
        this.activityUseCase = activityUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar activity terkini sebelum menu
            presenter.showActivitys(activityUseCase.getAllActivitys());
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addActivity();
                case "2" -> updateActivity();
                case "3" -> searchActivity();
                case "4" -> sortActivity();
                case "5" -> removeActivity();
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

    /** Form tambah activity baru. */
    private void addActivity() {
        System.out.println("[Menambah Kegiatan]");
        String title = InputUtil.input("Judul (x Jika Batal)");
        // Batalkan jika pengguna memilih 'x' atau tidak memasukkan judul
        if (title.equals("x") || title.isBlank()) {
            return;
        }

        String day = InputUtil.input("Hari (x Jika Batal)");
        if (day.equals("x") || day.isBlank()) {
            return;
        }

        String time = InputUtil.input("Waktu (x Jika Batal)");
        if (time.equals("x") || time.isBlank()) {
            return;
        }

        presenter.showAddSuccess(activityUseCase.addActivity(title, day, time));
    }

    /** Form hapus activity berdasarkan ID. */
    private void removeActivity() {
        System.out.println("[Menghapus Kegiatan]");
        String strIdActivity = InputUtil.input("[ID Kegiatan] yang dihapus (x Jika Batal)");

        if (strIdActivity.equals("x")) {
            return;
        }

        Integer id = parseId(strIdActivity);
        if (id == null) {
            return;
        }

        if (activityUseCase.removeActivity(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Form ubah judul dan/atau status activity. */
    private void updateActivity() {
        System.out.println("[Mengubah Kegiatan]");
        String strIdActivity = InputUtil.input("ID Kegiatan yang diubah (x Jika Batal)");

        if (strIdActivity.equals("x")) {
            return;
        }

        Integer id = parseId(strIdActivity);
        if (id == null) {
            return;
        }

        String newTitle = InputUtil.input("Judul Baru (Kosongkan jika tidak ingin mengubah)");
        String newDay = InputUtil.input("Hari Baru (Kosongkan jika tidak ingin mengubah)");
        String newTime = InputUtil.input("Waktu Baru (Kosongkan jika tidak ingin mengubah)");

        // null berarti field tersebut tidak diubah
        String title = newTitle.isBlank() ? null : newTitle;
        String day = newDay.isBlank() ? null : newDay;
        String time = newTime.isBlank() ? null : newTime;

        if (activityUseCase.updateActivity(id, title, day, time)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    /** Form cari activity berdasarkan kata kunci. */
    private void searchActivity() {
        System.out.println("[Mencari Kegiatan]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!keyword.equals("x")) {
            presenter.showSearchResults(activityUseCase.searchActivitys(keyword), keyword);
        }
    }

    /** Form urutkan activity berdasarkan pilihan user. */
    private void sortActivity() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Hari (Senin -> Minggu)");
        System.out.println("2. Waktu (Awal -> Akhir)");
        System.out.println("3. Judul (A-Z)");
        System.out.println("4. Judul (Z-A)");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        if (input.equals("x")) {
            return;
        }

        // Konversi input angka ke enum domain
        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedActivitys(activityUseCase.sortActivitys(option));
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

    /** Memetakan pilihan menu (1-4) ke {@link SortOption} domain. */
    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.DAY_ASC;
            case "2" -> SortOption.TIME_ASC;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
    }
}