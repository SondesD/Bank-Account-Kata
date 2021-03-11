package model;

import exception.NegativeAmountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;

    @DisplayName("The amount of a transaction shouldn't be negative")
    @ParameterizedTest
    @ValueSource(ints = { -5, -20, -100, -350 })
    void given_negativeAmount_then_buildTransaction_should_throws_negativeAmountException(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);

        for (TransactionType type : TransactionType.values()) {
            assertThrows(
                    NegativeAmountException.class,
                    () -> Transaction.build(amount, type, LocalDateTime.now()),
                    NegativeAmountException.NEGATIVE_AMOUNT_MESSAGE
            );
        }
    }

    @DisplayName("Given a valid amount of a transaction should build a transaction")
    @ParameterizedTest
    @ValueSource(ints = { 5, 20, 100, 350 })
    void given_validAmount_then_buildTransaction_should_return_transaction(int input) {
        BigDecimal amount = BigDecimal.valueOf(input);

        for (TransactionType type : TransactionType.values()) {
            assertNotNull(Transaction.build(amount, type, LocalDateTime.now()));
        }
    }

    @DisplayName("The transaction type should be Deposit or Withdrawl")
    @Test
    void given_unknownTransactionType_then_getSignedAmout_should_throws_unsupportedOperationException() {
        transaction = Transaction.build(BigDecimal.valueOf(12), TransactionType.Unknown, LocalDateTime.now());
        assertThrows(
                    UnsupportedOperationException.class,
                    () -> transaction.getSignedAmount());

    }

    @DisplayName("The Amount should be positive when the transaction type is Deposit")
    @Test
    void given_DepositTransactionType_then_getSignedAmout_should_returPositiveAmount() {
        transaction = Transaction.build(BigDecimal.valueOf(12), TransactionType.DEPOSIT, LocalDateTime.now());
        BigDecimal expectedAmount= BigDecimal.valueOf(12).setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(expectedAmount, transaction.getSignedAmount());

    }

    @DisplayName("The Amount should be negative when the transaction type is Withdrawl")
    @Test
    void given_WithdrawlTransactionType_then_getSignedAmout_should_returNegativeAmount() {
        transaction = Transaction.build(BigDecimal.valueOf(12), TransactionType.WITHDRAWAL, LocalDateTime.now());
        BigDecimal expectedAmount= BigDecimal.valueOf(-12).setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(expectedAmount, transaction.getSignedAmount());

    }


}