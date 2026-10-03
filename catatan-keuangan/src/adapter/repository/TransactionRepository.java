package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> data = new ArrayList<>();
    private int idCounter = 0;

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        Transaction transaction = new Transaction(nextId(), description, amount, type);
        data.add(transaction);
        return copyOf(transaction);
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> copies = new ArrayList<>();
        for (Transaction transaction : data) {
            copies.add(copyOf(transaction));
        }

        return copies;
    }

    @Override
    public boolean delete(int id) {
        return data.removeIf(transaction -> transaction.getId() == id);
    }

    /** Defensive copy: entity asli tidak pernah keluar dari repository. */
    private Transaction copyOf(Transaction transaction) {
        return new Transaction(transaction.getId(), transaction.getDescription(),
                transaction.getAmount(), transaction.getType());
    }

    private int nextId() {
        return ++idCounter;
    }
}
