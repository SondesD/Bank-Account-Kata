package exception;

public class IllegalTransactionException extends RuntimeException {

    private final String message;

    public IllegalTransactionException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
