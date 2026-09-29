package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ItemUseCase;

public class ItemView {
    private final ItemUseCase itemUseCase;

    private final ItemPresenter presenter;

    public ItemView(ItemUseCase itemUseCase, ItemPresenter presenter) {
        this.itemUseCase = itemUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showItems(itemUseCase.getAllItems());
            printMenu();

            String input = InputUtil.input("Pilih");
            if (input.isBlank()) {
                break;
            }

            switch (input) {
                case "1" -> addItem();
                case "2" -> updateItem();
                case "3" -> searchItem();
                case "4" -> sortItem();
                case "5" -> removeItem();
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
        System.out.println("2. Ubah Stok");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah item baru. */
    private void addItem() {
        System.out.println("[Menambah Barang]");
        String name = InputUtil.input("Nama (x Jika Batal)");

        if (name.isBlank() || name.equals("x")) {
            return;
        }

        if (!name.equals("x")) {
            String quantity = InputUtil.input("Jumlah");
            if (!isValidQuantity(quantity)) {
                presenter.showInvalidQuantity();
                return;
            }

            String category = InputUtil.input("Kategori (x Jika Batal)");
            if (category.equals("x")) {
                return;
            }

            presenter.showAddSuccess(itemUseCase.addItem(name, quantity, category));
        }
    }

    /** Form hapus item berdasarkan ID. */
    private void removeItem() {
        System.out.println("[Menghapus Barang]");
        String strIdItem = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");

        if (strIdItem.isBlank() || strIdItem.equals("x")) {
            return;
        }

        Integer id = parseId(strIdItem);
        if (id == null) {
            return;
        }

        if (itemUseCase.removeItem(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Form ubah judul dan/atau status item. */
    private void updateItem() {
        System.out.println("[Mengubah Stok]");
        String strIdItem = InputUtil.input("ID Barang yang diubah (x Jika Batal)");

        if (strIdItem.isBlank() || strIdItem.equals("x")) {
            return;
        }

        Integer id = parseId(strIdItem);
        if (id == null) {
            return;
        }

        String newQuantity = InputUtil.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)");
        if (!newQuantity.isBlank() && !isValidQuantity(newQuantity)) {
            presenter.showInvalidQuantity();
            return;
        }

        String quantity = newQuantity.isBlank() ? null : newQuantity;

        if (itemUseCase.updateItem(id, null, quantity, null)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    /** Form cari item berdasarkan kata kunci. */
    private void searchItem() {
        System.out.println("[Mencari Barang]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!keyword.isBlank() && !keyword.equals("x")) {
            presenter.showSearchResults(itemUseCase.searchItems(keyword), keyword);
        }
    }

    /** Form urutkan item berdasarkan pilihan user. */
    private void sortItem() {
        System.out.println("[Mengurutkan Barang]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah (Terkecil -> Terbesar)");
        System.out.println("4. Jumlah (Terbesar -> Terkecil)");
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

        presenter.showSortedItems(itemUseCase.sortItems(option));
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

    private boolean isValidQuantity(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Memetakan pilihan menu (1-2) ke {@link SortOption} domain. */
    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> null;
        };
    }
}