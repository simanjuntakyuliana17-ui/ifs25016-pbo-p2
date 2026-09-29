package domain.entity;

import java.util.Comparator;

public enum SortOption {
    /** Urutkan name dari A ke Z (case-insensitive). */
    NAME_ASC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan name dari Z ke A (case-insensitive). */
    NAME_DESC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar todo. */
    private final Comparator<Contact> comparator;

    SortOption(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Contact> comparator() {
        return comparator;
    }
}