package adapter.presenter;

import domain.entity.Todo;
import java.util.List;

public class TodoPresenter {

    private String format(Todo todo) {
        // <id> | <judul> | <status>
        String status = todo.isFinished() ? "Selesai" : "Belum Selesai";
        return String.format("%d | %s | %s", todo.getId(), todo.getTitle(), status);
    }

    private void printList(List<Todo> todos, String header, String emptyMessage) {
        System.out.println(header);

        if (todos.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Todo todo : todos) {
            System.out.println(format(todo));
        }
    }

    public void showTodos(List<Todo> todos) {
        printList(todos, "Daftar Todo:", "- Data todo belum tersedia!");
    }

    public void showSearchResults(List<Todo> todos, String keyword) {
        printList(todos, "Hasil Pencarian: \"" + keyword + "\"", "- Todo tidak ditemukan!");
    }

    public void showSortedTodos(List<Todo> todos) {
        printList(todos, "Daftar Todo (Terurut):", "- Data todo belum tersedia!");
    }

    public void showAddSuccess(Todo todo) {
        System.out.printf("Berhasil menambah todo: %s%n", format(todo));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus todo.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus todo dengan ID: %d.%n", id);
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah todo.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah todo dengan ID: %d.%n", id);
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
