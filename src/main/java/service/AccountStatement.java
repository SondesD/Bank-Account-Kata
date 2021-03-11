package service;

import model.Transaction;
import util.DateUtils;

import java.math.BigDecimal;
import java.util.List;

public class AccountStatement {

    private static final String OPERATION_FORMAT = "%s - %s of %s. Balance: %s";

    static void printStatement( List<Transaction> transactions) {
        BigDecimal balance = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            balance = balance.add(transaction.getSignedAmount());
            String line = String.format(OPERATION_FORMAT,
                    DateUtils.getFormattedDate(transaction.getTransactionDate()),
                    transaction.getTransactionType(),
                    transaction.getAmount(),
                    balance);

            System.out.println(line);
        }
    }


}
