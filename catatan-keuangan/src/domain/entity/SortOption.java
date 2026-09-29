package domain.entity;

import java.util.Comparator;

public enum SortOption {
    AMOUNT_ASC(Comparator.comparingLong(Transaction::getAmount)),
    AMOUNT_DESC(Comparator.comparingLong(Transaction::getAmount).reversed()),
    INCOME_FIRST(Comparator.comparingInt((Transaction transaction) ->
            transaction.getType() == TransactionType.INCOME ? 0 : 1)
            .thenComparing(Comparator.comparingLong(Transaction::getAmount).reversed())),
    EXPENSE_FIRST(Comparator.comparingInt((Transaction transaction) ->
            transaction.getType() == TransactionType.EXPENSE ? 0 : 1)
            .thenComparing(Comparator.comparingLong(Transaction::getAmount).reversed()));

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> transactionComparator() {
        return comparator;
    }
}
