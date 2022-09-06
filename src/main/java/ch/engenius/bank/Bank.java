package ch.engenius.bank;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<Integer, Account> accounts = new HashMap<>();

    /**
     * Register new account.
     *
     * @param accountNumber an account number to be assigned to new account
     * @param amount        an amount for new account
     * @return registered account
     * @throws IllegalStateException if account has been already created
     */
    public synchronized Account registerAccount(int accountNumber, BigDecimal amount) {
        if (isAccountCreated(accountNumber)) {
            throw new IllegalStateException(String.format("Account with number %s already created!", accountNumber));
        }

        Account account = new Account(amount);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * Gets the account by account number.
     *
     * @param number an account number by which account will be retrieved
     * @return fount account or null if count was not creted
     */
    public synchronized Account getAccount(int number) {
        return accounts.get(number);
    }

    private boolean isAccountCreated(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }
}
