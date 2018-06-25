package org.art.java_core.procedural.Cashpoint;

import java.util.Scanner;

/**
 * Simple money cashpoint implementation.
 */
public class Cashpoint implements Runnable {

    private static final int DEFAULT_20_BANKNOTES_NUMBER = 5;
    private static final int DEFAULT_50_BANKNOTES_NUMBER = 10;
    private static final int DEFAULT_100_BANKNOTES_NUMBER = 5;

    private static final String DOLLARS_20 = "20 dollars";
    private static final String DOLLARS_50 = "50 dollars";
    private static final String DOLLARS_100 = "100 dollars";

    private int current20banknotes = DEFAULT_20_BANKNOTES_NUMBER;
    private int current50banknotes = DEFAULT_50_BANKNOTES_NUMBER;
    private int current100banknotes = DEFAULT_100_BANKNOTES_NUMBER;

    private int banknotes20Available;
    private int banknotes50Available;
    private int banknotes100Available;

    private Scanner scanner = new Scanner(System.in);

    public Cashpoint() {
    }

    private void initCashpoint() {
        System.out.println("Enter the default number of banknotes in the cashpoint!");
        this.current20banknotes = getNumberFromScanner("Enter the number of banknotes in 20 dollars:");
        this.current50banknotes = getNumberFromScanner("Enter the number of banknotes in 50 dollars:");
        this.current100banknotes = getNumberFromScanner("Enter the number of banknotes in 100 dollars:");
    }

    private int getOption() {
        System.out.println("Enter \"1\" to add money to the cashpoint.");
        System.out.println("Enter \"2\" to withdraw money from the cashpoint.");
        System.out.println("Enter \"3\" to check the amount of money in the cashpoint.");
        System.out.println("Enter \"4\" to exit.");
        int option = scanner.nextInt();
        if (option < 1 || option > 4) {
            return -1;
        }
        return option;
    }

    private void addMoneyToTheCashpoint() {
        System.out.println("Enter the number of banknotes to add to the cashpoint!");
        this.current20banknotes += getNumberFromScanner("Enter the number of banknotes in 20 dollars to add:");
        this.current50banknotes += getNumberFromScanner("Enter the number of banknotes in 50 dollars to add:");
        this.current100banknotes += getNumberFromScanner("Enter the number of banknotes in 100 dollars add:");
        System.out.println("The banknotes successfully added!\n");
    }

    private boolean withdrawMoney(int moneyToWithdraw) {
        resetAvailable();
        int moneyInCashpoint = current20banknotes * 20 + current50banknotes * 50 + current100banknotes * 100;
        if (moneyToWithdraw > moneyInCashpoint) {
            System.out.println("Not enough money in the cashpoint!");
            return false;
        }
        moneyToWithdraw = generateMoney(moneyToWithdraw);
        if (moneyToWithdraw == 0) {
            System.out.println("Take your money:");
            if (banknotes20Available != 0) {
                System.out.println(DOLLARS_20 + ": " + banknotes20Available + " banknote(s).");
            }
            if (banknotes50Available != 0) {
                System.out.println(DOLLARS_50 + ": " + banknotes50Available + " banknote(s).");
            }
            if (banknotes100Available != 0) {
                System.out.println(DOLLARS_100 + ": " + banknotes100Available + " banknote(s).");
            }
            return true;
        } else if ((moneyToWithdraw % 10 == 0) && moneyToWithdraw > 10) {
            resetAmountOfMoney();
            System.out.println("It's not enough banknotes in the cashpoint.");
            return false;
        } else {
            resetAmountOfMoney();
            System.out.println("Incorrect amount of money to withdraw.");
            return false;
        }
    }

    private boolean isEnough100() {
        return this.current100banknotes > 0;
    }

    private boolean isEnough50() {
        return this.current50banknotes > 0;
    }

    private boolean isEnough20() {
        return this.current20banknotes > 0;
    }

    private void showMoneyInCashpoint() {
        System.out.printf("The remaining number of banknotes (20 dollars) in the cashpoint is: %d.%n", this.current20banknotes);
        System.out.printf("The remaining number of banknotes (50 dollars) in the cashpoint is: %d.%n", this.current50banknotes);
        System.out.printf("The remaining number of banknotes (100 dollars) in the cashpoint is: %d.%n", this.current100banknotes);
    }

    private int generateMoney(int amountOfMoney) {
        while (amountOfMoney >= 100 && isEnough100() && !((amountOfMoney - 100 < 40)
                && ((amountOfMoney - 100) % 20 != 0))) {
            amountOfMoney -= 100;
            this.current100banknotes--;
            this.banknotes100Available++;
        }
        while (amountOfMoney >= 50 && isEnough50() && !((amountOfMoney - 50 < 40)
                && ((amountOfMoney - 50) % 20 != 0)) && !(this.current50banknotes == 1
                && (amountOfMoney - 50) % 20 != 0)) {
            amountOfMoney -= 50;
            this.current50banknotes--;
            this.banknotes50Available++;
        }
        while (amountOfMoney >= 20 && isEnough20()) {
            amountOfMoney -= 20;
            this.current20banknotes--;
            this.banknotes20Available++;
        }
        return amountOfMoney;
    }

    private void resetAmountOfMoney() {
        //The banknotes returning after failing
        this.current20banknotes += banknotes20Available;
        this.current50banknotes += banknotes50Available;
        this.current100banknotes += banknotes100Available;
    }

    private void resetAvailable() {
        this.banknotes20Available = 0;
        this.banknotes50Available = 0;
        this.banknotes100Available = 0;
    }

    private int getNumberFromScanner(String message) {
        int number;
        do {
            System.out.println(message);
            number = scanner.nextInt();
        } while (number < 0);
        return number;
    }

    @Override
    public void run() {
        System.out.println("*** Welcome to the Cashpoint ***");
        //Cashpoint initialization
        initCashpoint();

        //Cashpoint running
        while (true) {
            int option = getOption();
            if (option == 1) {
                addMoneyToTheCashpoint();
            } else if (option == 2) {
                int moneyToWithdraw;
                moneyToWithdraw = getNumberFromScanner("Enter the amount of money to withdraw from the cashpoint:");
                boolean operationResult = withdrawMoney(moneyToWithdraw);
                if (operationResult) {
                    System.out.println("The operation was successfully completed!");
                } else {
                    System.out.println("The operation failed. Try again!");
                }
            } else if (option == 3) {
                showMoneyInCashpoint();
            } else if (option == 4) {
                System.out.println("*** Good Bye!!! ***");
                //Exit from the cashpoint
                break;
            } else if (option == -1) {
                System.out.println("Incorrect option. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        //Cashpoint launching
        new Thread(new Cashpoint()).start();
    }
}
