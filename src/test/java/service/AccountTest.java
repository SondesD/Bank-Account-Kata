package service;

import exception.OverdraftException;
import model.Transaction;
import model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import util.DateUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {
    private Account account;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        account = new Account(
                new AccountHistory()
        );
    }

    @DisplayName("When an account is created, it shouldn't have any money")
    @Test
    void when_accountCreation_then_balance_should_be_equals_to_zero() {
        assertEquals(BigDecimal.ZERO , account.getBalance());
    }

    @DisplayName("When a deposit is made, the balance should increase accordingly")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void when_deposit_then_balance_should_be_increase(int inputAmount) {
        BigDecimal amount = BigDecimal.valueOf(inputAmount).setScale(2, RoundingMode.HALF_EVEN);
        account.deposit(amount);
        assertEquals(amount, account.getBalance());
    }


    @DisplayName("When a withdrawal is made, the balance should decrease accordingly")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void withdrawalTest(int inputAmount) {
        BigDecimal amount = BigDecimal.valueOf(inputAmount).setScale(2, RoundingMode.HALF_EVEN);
        account.deposit(amount);
        assertEquals(amount, account.getBalance());
        account.withdrawal(amount);
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN) , account.getBalance());
    }

    @DisplayName("A withdrawal shouldn't result in an overdraft")
    @ParameterizedTest
    @ValueSource(ints = { 1, 10, 50, 200 })
    void when_insufficientFunds_then_withdrawl_should_throws_OverdraftException(int inputAmount) {
        BigDecimal amount = BigDecimal.valueOf(inputAmount);
        assertThrows(
                OverdraftException.class,
                () -> account.withdrawal(amount),
                OverdraftException.OVERDRAFT_MESSAGE
        );
    }

    @DisplayName("The balance should be equals to the sum of all transactions")
    @Test
    void balance_should_be_equals_to_the_sum_of_all_transactions() {
        List<Transaction> transactions = List.of(
                Transaction.build(BigDecimal.TEN, TransactionType.DEPOSIT, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.WITHDRAWAL, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.DEPOSIT, LocalDateTime.now()),
                Transaction.build(BigDecimal.TEN, TransactionType.WITHDRAWAL, LocalDateTime.now()),
                Transaction.build(BigDecimal.ONE, TransactionType.DEPOSIT, LocalDateTime.now())
        );

        AccountHistory accountHistory = new AccountHistory();
        transactions.forEach(accountHistory::addTransaction);

        account = new Account(accountHistory);
        assertEquals(BigDecimal.ONE.setScale(2, RoundingMode.HALF_EVEN) , account.getBalance());
    }

    @DisplayName("When printing the account statement, it should display the date, type, amount and balance for every transaction")
    @Test
    void when_printStatement_should_display_all_details_of_transaction() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        account = new Account(
                new AccountHistory());

        account.deposit(BigDecimal.TEN);
        account.withdrawal(BigDecimal.ONE);
        account.deposit(BigDecimal.TEN);

        account.printStatement();

        String printedDate = DateUtils.getFormattedDate(LocalDateTime.now());

        String expected =printedDate + " - DEPOSIT of 10.00. Balance: 10.00" + System.lineSeparator() +
                printedDate + " - WITHDRAWAL of 1.00. Balance: 9.00" + System.lineSeparator() +
                printedDate + " - DEPOSIT of 10.00. Balance: 19.00" + System.lineSeparator();

        assertEquals(expected , outContent.toString());

    }

}