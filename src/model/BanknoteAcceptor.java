package model;

import java.util.Scanner;

public class BanknoteAcceptor implements MoneyAcceptor {
    private int balance;
    private final Scanner scanner;

    public BanknoteAcceptor() {
        this.balance = 0;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void deductAmount(int amount) {
        this.balance -= amount;
    }

    @Override
    public int acceptMoney() {
        System.out.println("Введите номинал купюры для пополнения (50 или 100):");
        int banknote = scanner.nextInt();
        if (banknote == 50 || banknote == 100) {
            this.balance += banknote;
            System.out.println("Вы пополнили баланс на " + banknote + ".");
            return banknote;
        } else {
            System.out.println("Принимаются только купюры 50 и 100.");
            return 0;
        }
    }
}
