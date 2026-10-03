package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final FinanceUseCase financeUseCase;
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase financeUseCase, FinancePresenter presenter) {
        this.financeUseCase = financeUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showTransactions(financeUseCase.getAllTransactions(), financeUseCase.getBalance());
            printMenu();

            String input = InputUtil.input("Pilih");
            if (input.isBlank()) {
                break;
            }

            switch (input) {
                case "1" -> addTransaction(TransactionType.INCOME);
                case "2" -> addTransaction(TransactionType.EXPENSE);
                case "3" -> searchTransactions();
                case "4" -> showSortedTransactions();
                case "5" -> showBalance();
                case "6" -> deleteTransaction();
                case "x", "X" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    private void addTransaction(TransactionType type) {
        String title = type == TransactionType.INCOME ? "[Tambah Pemasukan]" : "[Tambah Pengeluaran]";
        System.out.println(title);
        String description = InputUtil.input("Keterangan (x Jika Batal)");
        if (description.isBlank() || "x".equalsIgnoreCase(description)) {
            return;
        }

        String amountInput = InputUtil.input("Jumlah");
        long amount;
        try {
            amount = Long.parseLong(amountInput);
        } catch (NumberFormatException e) {
            presenter.showInvalidAmount();
            return;
        }

        if (amount <= 0) {
            presenter.showInvalidAmount();
            return;
        }

        Transaction transaction = financeUseCase.addTransaction(description, amount, type);
        if (transaction != null) {
            presenter.showAddSuccess(transaction);
        } else {
            presenter.showInvalidAmount();
        }
    }

    private void searchTransactions() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.isBlank() || "x".equalsIgnoreCase(keyword)) {
            return;
        }

        presenter.showSearchResult(keyword, financeUseCase.searchTransactions(keyword));
    }

    private void showSortedTransactions() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");
        String input = InputUtil.input("Pilih");
        if (input.isBlank() || "x".equalsIgnoreCase(input)) {
            return;
        }

        SortOption option = mapSortOption(input);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedTransactions(financeUseCase.sortTransactions(option));
    }

    private void deleteTransaction() {
        presenter.showDeleteTransaction();
        String input = InputUtil.input("ID Transaksi (x Jika Batal)");
        if (input.isBlank() || "x".equalsIgnoreCase(input)) {
            return;
        }

        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            presenter.showInvalidTransactionId();
            return;
        }

        boolean removed = financeUseCase.deleteTransaction(id);
        if (removed) {
            presenter.showDeleteSuccess();
        } else {
            presenter.showDeleteFailed(id);
        }
    }

    private void showBalance() {
        System.out.println("Saldo saat ini: Rp " + financeUseCase.getBalance());
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.AMOUNT_ASC;
            case "2" -> SortOption.AMOUNT_DESC;
            case "3" -> SortOption.INCOME_FIRST;
            case "4" -> SortOption.EXPENSE_FIRST;
            default -> null;
        };
    }
}
