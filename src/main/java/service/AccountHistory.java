package service;

import exception.OverdraftException;
import model.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountHistory {

    private final List<Transaction> transactions = new ArrayList<>();

    void addTransaction(Transaction transaction) {
        if (getBalance().add(transaction.getSignedAmount()).signum() < 0) {
            throw new OverdraftException();
        }
        transactions.add(transaction);
    }

    BigDecimal getBalance() {
        return transactions.stream()
                .map(Transaction::getSignedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

}
