package ch.engenius.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BankRunner {

    private static final ExecutorService executor = Executors.newFixedThreadPool(8);
    public static final Logger logger = LoggerFactory.getLogger(BankRunner.class);

    private final Random random = new Random(43);
    private final Bank bank = new Bank();


    public static void main(String[] args) {
        BankRunner runner = new BankRunner();
        int accounts = 100;
        int defaultDeposit = 1000;
        int iterations = 10000;
        runner.registerAccounts(accounts, defaultDeposit);
        runner.sanityCheck(accounts, accounts * defaultDeposit);
        runner.runBank(iterations, accounts);
        runner.sanityCheck(accounts, accounts * defaultDeposit);

    }

    private void runBank(int iterations, int maxAccount) {
        for (int i = 0; i < iterations; i++) {
            executor.submit(() -> runRandomOperation(maxAccount));
        }
        try {
            executor.shutdown();
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Exception while trying to shutdown the executor!", e);
        }
    }

    private void runRandomOperation(int maxAccount) {
        BigDecimal transfer = BigDecimal.valueOf(random.nextDouble()).multiply(BigDecimal.valueOf(100.0));
        int accountInNumber = random.nextInt(maxAccount);
        int accountOutNumber = random.nextInt(maxAccount);
        Account accIn = bank.getAccount(accountInNumber);
        Account accOut = bank.getAccount(accountOutNumber);
        transferMoney(accOut, accIn, transfer);
    }

    private void transferMoney(Account sourceAccount, Account targetAccount, BigDecimal transferAmount) {
        sourceAccount.withdraw(transferAmount);
        targetAccount.deposit(transferAmount);
    }

    private void registerAccounts(int number, int defaultMoney) {
        for (int i = 0; i < number; i++) {
            bank.registerAccount(i, BigDecimal.valueOf(defaultMoney));
        }
    }

    private void sanityCheck(int accountMaxNumber, int totalExpectedMoney) {
        BigDecimal sum = IntStream.range(0, accountMaxNumber)
                .mapToObj(bank::getAccount)
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.intValue() != totalExpectedMoney) {
            throw new IllegalStateException(String.format("we got %s != %s (expected)", sum, totalExpectedMoney));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("sanity check OK");
        }
    }
}
