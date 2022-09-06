package ch.engenius.bank;

import java.math.BigDecimal;

public class Account {
    private BigDecimal money;

    /**
     * Creates account with initial amount of money.
     *
     * @param money a money amount for new account
     */
    public Account(BigDecimal money) {
        this.money = money;
    }

    /**
     * Withdraw money amount from account.
     *
     * @param amount an amount to be withdrawn from account
     * @throws IllegalStateException if there is not enough money within the account to be withdrawn
     */
    public synchronized void withdraw(BigDecimal amount) {
        if (isAccountHaveEnoughMoneyForWithdraw(amount)) {
            throw new IllegalStateException("Not enough credits on account!");
        }

        setMoney(money.subtract(amount));
    }

    /**
     * Deposit money amount to account.
     *
     * @param amount an amount to be deposit to account
     * @throws IllegalStateException if negative ammount is provided
     */
    public void deposit(BigDecimal amount) {
        if (isNegativeDeposit(amount)) {
            throw new IllegalStateException("Deposit cannot be negative amount!");
        }

        synchronized (this) {
            setMoney(money.add(amount));
        }
    }

    /**
     * Gets the money amount within current account.
     *
     * @return money amount
     */
    public synchronized BigDecimal getMoney() {
        return money;
    }

    private void setMoney(BigDecimal money) {
        this.money = money;
    }

    private boolean isAccountHaveEnoughMoneyForWithdraw(BigDecimal withdrawAmount) {
        return (money.subtract(withdrawAmount)).compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isNegativeDeposit(BigDecimal depositAmount) {
        return depositAmount.compareTo(BigDecimal.ZERO) < 0;
    }
}
