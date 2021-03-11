package service;

import exception.OverdraftException;
import model.Transaction;
import model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountHistoryTest {

    private AccountHistory accountHistory;

    @BeforeEach
    void setUp() {
        accountHistory = new AccountHistory();
    }

    @DisplayName("The added transactions should be saved")
    @Test
    void addTransaction_should_save_transactions() {
        List<Transaction> transactions = List.of(
                Transaction.build(BigDecimal.TEN, TransactionType.DEPOSIT, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.WITHDRAWAL, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.DEPOSIT, LocalDateTime.now()),
                Transaction.build(BigDecimal.TEN, TransactionType.WITHDRAWAL, LocalDateTime.now())
        );

        transactions.forEach(t -> accountHistory.addTransaction(t));

        assertThat(accountHistory.getTransactions()).isEqualTo(transactions);
    }


    @DisplayName("Overdraft is not allowed")
    @Test
    void given_insufficientFunds_then_addTransaction_should_throws_overdraftException() {
        List<Transaction> transactions = List.of(
                Transaction.build(BigDecimal.TEN, TransactionType.DEPOSIT, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.WITHDRAWAL, LocalDateTime.now()),
                Transaction.build(BigDecimal.TEN, TransactionType.WITHDRAWAL, LocalDateTime.now())
        );
        assertThrows(
                OverdraftException.class,
                () ->  transactions.forEach(t -> accountHistory.addTransaction(t)),
                OverdraftException.OVERDRAFT_MESSAGE
        );

    }


}