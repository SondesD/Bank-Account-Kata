package service;

import model.Transaction;
import model.TransactionType;
import util.DateUtils;

import java.math.BigDecimal;
import java.util.List;

public class Account {

    private final AccountHistory accountHistory;

    public Account(AccountHistory accountHistory) {
        this.accountHistory = accountHistory;
    }

    BigDecimal getBalance() {
        return accountHistory.getBalance();
    }

    public void deposit(BigDecimal amount) {
        Transaction deposit = Transaction.build(amount, TransactionType.DEPOSIT, DateUtils.getDate());
        accountHistory.addTransaction(deposit);
    }

    public void withdrawal(BigDecimal amount) {
        Transaction withdrawal = Transaction.build(amount, TransactionType.WITHDRAWAL, DateUtils.getDate());
        accountHistory.addTransaction(withdrawal);
    }

    public void printStatement() {
        List<Transaction> transactions = accountHistory.getTransactions();
        AccountStatement.printStatement(transactions);
    }
}
