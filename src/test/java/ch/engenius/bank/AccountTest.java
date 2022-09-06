package ch.engenius.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    Account account;
    static BigDecimal INITIAL_POSITIVE_AMOUNT = BigDecimal.valueOf(123_456.99);
    static BigDecimal WITHDRAW_AMOUNT = BigDecimal.valueOf(15);
    static BigDecimal AMOUNT_AFTER_WITHDRAW = BigDecimal.valueOf(123_441.99);
    static BigDecimal AMOUNT_AFTER_DOUBLE_WITHDRAW = BigDecimal.valueOf(123_426.99);
    static BigDecimal WITHDRAW_UNAVAILABLE_AMOUNT = BigDecimal.valueOf(200_000);
    static BigDecimal NEGATIVE_DEPOSIT = BigDecimal.valueOf(-123_456.99);
    static BigDecimal DEPOSIT_AMOUNT = BigDecimal.valueOf(15);

    @BeforeEach
    void setUp() {
        account = new Account(INITIAL_POSITIVE_AMOUNT);
    }

    @Test
    void testGetAmount() {
        assertEquals(0, INITIAL_POSITIVE_AMOUNT.compareTo(account.getMoney()));
    }

    @Test
    void testWithdrawAmount() {
        account.withdraw(WITHDRAW_AMOUNT);

        assertEquals(0, AMOUNT_AFTER_WITHDRAW.compareTo(account.getMoney()));
    }

    @Test
    void testWithdrawUnavailableAmount() {
        assertThrows(IllegalStateException.class,
                () -> account.withdraw(WITHDRAW_UNAVAILABLE_AMOUNT));
    }

    @Test
    void testDoubleWithdrawAmount() {
        account.withdraw(WITHDRAW_AMOUNT);
        account.withdraw(WITHDRAW_AMOUNT);

        assertEquals(0, AMOUNT_AFTER_DOUBLE_WITHDRAW.compareTo(account.getMoney()));
    }

    @Test
    void testWithdrawDepositAmount() {
        account.withdraw(WITHDRAW_AMOUNT);
        account.deposit(DEPOSIT_AMOUNT);

        assertEquals(0, INITIAL_POSITIVE_AMOUNT.compareTo(account.getMoney()));
    }

    @Test
    void testNegativeDeposit() {
        assertThrows(IllegalStateException.class,
                () -> account.deposit(NEGATIVE_DEPOSIT));
    }
}