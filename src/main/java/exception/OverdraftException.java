package exception;

public class OverdraftException extends IllegalTransactionException {

        public static final String OVERDRAFT_MESSAGE = "Overdraft is not allowed";

        public OverdraftException() {
            super(OVERDRAFT_MESSAGE);
        }

        @Override
        public String toString() {
            return super.toString();
        }

}
