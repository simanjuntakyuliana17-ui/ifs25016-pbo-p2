package domain.entity;

import java.util.Comparator;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public enum SortOption {
    /** Urutkan hari dari Senin -> Minggu. */
    DAY_ASC(Comparator.comparingInt((Activity a) -> dayOrder(a.getDay()))
            .thenComparing((Activity a) -> parseTime(a.getTime()))),

    /** Urutkan waktu dari awal -> akhir (misal 06:00, 09:00). */
    TIME_ASC(Comparator.comparing((Activity a) -> parseTime(a.getTime()))),

    /** Urutkan judul dari A ke Z (case-insensitive). */
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul dari Z ke A (case-insensitive). */
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar kegiatan. */
    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Activity> comparator() {
        return comparator;
    }

    private static int dayOrder(String day) {
        if (day == null) return 7;
        return switch (day.toLowerCase()) {
            case "senin" -> 1;
            case "selasa" -> 2;
            case "rabu" -> 3;
            case "kamis" -> 4;
            case "jumat" -> 5;
            case "sabtu" -> 6;
            case "minggu" -> 7;
            default -> 7;
        };
    }

    private static LocalTime parseTime(String time) {
        if (time == null) return LocalTime.MIDNIGHT;
        String t = time.trim();
        try {
            return LocalTime.parse(t);
        } catch (DateTimeParseException e) {
            // try single-digit hour like "6:00"
            if (t.matches("\\d:.*")) {
                try {
                    return LocalTime.parse("0" + t);
                } catch (DateTimeParseException ignored) {
                }
            }
            return LocalTime.MIDNIGHT;
        }
    }
}
