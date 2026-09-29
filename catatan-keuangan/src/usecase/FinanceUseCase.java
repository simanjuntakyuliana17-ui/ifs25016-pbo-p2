package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.Comparator;
import java.util.List;

public class FinanceUseCase {
    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(String description, long amount, TransactionType type) {
        if (amount <= 0) {
            return null;
        }

        return transactionRepository.save(description, amount, type);
    }

    public long getBalance() {
        long balance = 0;
        for (Transaction transaction : transactionRepository.findAll()) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance += transaction.getAmount();
            } else {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getDescription().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    public boolean deleteTransaction(int id) {
        return transactionRepository.delete(id);
    }

    public List<Transaction> sortTransactions(SortOption option) {
        Comparator<Transaction> comparator = option.transactionComparator();
        return transactionRepository.findAll().stream().sorted(comparator).toList();
    }
}
