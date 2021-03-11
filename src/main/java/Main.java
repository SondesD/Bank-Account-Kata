import service.Account;
import service.AccountHistory;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Account account = new Account(
                new AccountHistory()
        );
        try {
            account.deposit(BigDecimal.valueOf(350));
            account.withdrawal(BigDecimal.valueOf(150));
            account.deposit(BigDecimal.valueOf(200));
            account.withdrawal(BigDecimal.valueOf(30));
            account.withdrawal(BigDecimal.valueOf(80));

            account.printStatement();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
