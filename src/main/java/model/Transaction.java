package model;

import exception.NegativeAmountException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Transaction {

    private LocalDateTime transactionDate;

    private TransactionType transactionType;

    private BigDecimal amount;

    public Transaction(BigDecimal amount, TransactionType type, LocalDateTime date) {
        this.amount = amount;
        this.transactionType = type;
        this.transactionDate = date;
    }

    public static Transaction build(BigDecimal amount, TransactionType type, LocalDateTime date) {

        if (amount.signum() < 0) {
            throw new NegativeAmountException();
        }
        return new Transaction(amount.setScale(2, RoundingMode.HALF_EVEN), type, date);
    }

    public BigDecimal getSignedAmount() {
        if (transactionType == TransactionType.DEPOSIT)
            return amount;

        if (transactionType == TransactionType.WITHDRAWAL)
            return amount.negate();

        throw new UnsupportedOperationException(transactionType.toString());
    }
}
