package domain.entity;

import java.util.Comparator;

public enum SortOption {
    /** Urutkan name dari A ke Z (case-insensitive). */
    NAME_ASC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan name dari Z ke A (case-insensitive). */
    NAME_DESC(Comparator.comparing(Item::getName, String.CASE_INSENSITIVE_ORDER).reversed()),

    /** Urutkan quantity dari terkecil ke terbesar. */
    QUANTITY_ASC(Comparator.comparingInt(Item::getQuantity)),

    /** Urutkan quantity dari terbesar ke terkecil. */
    QUANTITY_DESC(Comparator.comparingInt(Item::getQuantity).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar barang. */
    private final Comparator<Item> comparator;

    SortOption(Comparator<Item> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Item> comparator() {
        return comparator;
    }
}
