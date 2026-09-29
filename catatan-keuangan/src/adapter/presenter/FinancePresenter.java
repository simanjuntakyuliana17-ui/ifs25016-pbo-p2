package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

public class FinancePresenter {
    public void showTransactions(List<Transaction> transactions, long balance) {
        System.out.println("Daftar Transaksi:");
        if (transactions.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            printTransactions(transactions);
        }
        System.out.println("Saldo: Rp " + balance);
    }

    public void showAddSuccess(Transaction transaction) {
        System.out.printf("Berhasil menambah transaksi: %d | %s | Rp %d | %s%n",
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                formatType(transaction.getType()));
    }

    public void showSearchResult(String keyword, List<Transaction> transactions) {
        System.out.printf("Hasil Pencarian: \"%s\"%n", keyword);
        if (transactions.isEmpty()) {
            System.out.println("- Transaksi tidak ditemukan!");
        } else {
            printTransactions(transactions);
        }
    }

    public void showSortedTransactions(List<Transaction> transactions) {
        System.out.println("Daftar Transaksi (Terurut):");
        printTransactions(transactions);
    }

    public void showInvalidAmount() {
        System.out.println("[!] Jumlah tidak valid!");
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    public void showSearchNotAvailable() {
        System.out.println("[Cari Transaksi]");
    }

    public void showDeleteTransaction() {
        System.out.println("[Hapus Transaksi]");
    }

    public void showDeleteSuccess() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showDeleteFailed(int id) {
        System.out.printf("[!] Gagal menghapus transaksi dengan ID: %d.%n", id);
    }

    public void showInvalidTransactionId() {
        System.out.println("[!] ID tidak valid!");
    }

    private void printTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.printf("%d | %s | Rp %d | %s%n",
                    transaction.getId(),
                    transaction.getDescription(),
                    transaction.getAmount(),
                    formatType(transaction.getType()));
        }
    }

    private String formatType(domain.entity.TransactionType type) {
        return type == domain.entity.TransactionType.INCOME ? "Pemasukan" : "Pengeluaran";
    }
}
