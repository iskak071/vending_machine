package model;

import java.util.Scanner;

public class CardAcceptor implements MoneyAcceptor {
    private int balance;
    private final Scanner scanner;

    public CardAcceptor() {
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
    System.out.println("Пожалуйста, введите номер карты:");
    scanner.nextLine();
    System.out.println("Введите одноразовый пароль:");
    scanner.nextLine();
    this.balance += 100;
    System.out.println("Оплата прошла успешно! Баланс пополнен на 100!");
    return 100;
    }
}
