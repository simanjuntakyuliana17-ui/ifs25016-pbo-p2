package domain.entity;

import java.util.Comparator;

public enum SortOption {
    /** Urutkan judul dari A ke Z (case-insensitive). */
    TITLE_ASC(Comparator.comparing(Todo::getTitle, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul dari Z ke A (case-insensitive). */
    TITLE_DESC(Comparator.comparing(Todo::getTitle, String.CASE_INSENSITIVE_ORDER).reversed()),

    /** Tampilkan todo yang belum selesai terlebih dahulu. */
    UNFINISHED_FIRST(Comparator.comparing(Todo::isFinished)),

    /** Tampilkan todo yang sudah selesai terlebih dahulu. */
    FINISHED_FIRST(Comparator.comparing(Todo::isFinished).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar todo. */
    private final Comparator<Todo> comparator;

    SortOption(Comparator<Todo> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Todo> comparator() {
        return comparator;
    }
}
