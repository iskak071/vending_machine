package model;

public interface MoneyAcceptor {
    int acceptMoney();

    int getBalance();

    void deductAmount(int amount);
}