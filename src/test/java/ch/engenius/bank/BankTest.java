package ch.engenius.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank;
    static int ACCOUNT_NUMBER = 1;
    static int NOT_REGISTERED_ACCOUNT_NUMBER = 2;
    static BigDecimal ACCOUNT_AMOUNT = BigDecimal.valueOf(100);

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void registerAccount() {
        Account account = bank.registerAccount(ACCOUNT_NUMBER, ACCOUNT_AMOUNT);

        assertAll(
                () -> assertEquals(bank.getAccount(ACCOUNT_NUMBER), account),
                () -> assertEquals(0, ACCOUNT_AMOUNT.compareTo(account.getMoney()))
        );
    }

    @Test
    void registerRegisteredAccount() {
        bank.registerAccount(ACCOUNT_NUMBER, ACCOUNT_AMOUNT);

        assertThrows(IllegalStateException.class,
                () -> bank.registerAccount(ACCOUNT_NUMBER, ACCOUNT_AMOUNT));
    }

    @Test
    void retrieveNotRegisteredAccount() {
        bank.registerAccount(ACCOUNT_NUMBER, ACCOUNT_AMOUNT);
        Account account = bank.getAccount(NOT_REGISTERED_ACCOUNT_NUMBER);

        assertNull(account);
    }
}