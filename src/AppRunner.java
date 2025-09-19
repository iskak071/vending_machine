import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final MoneyAcceptor moneyAcceptor;

    private static boolean isExit = false;

    private static final Scanner scanner = new Scanner(System.in);

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        System.out.println("Выберите способ оплаты:");
        System.out.println("1 - Наличными");
        System.out.println("2 - Картой");
        String choicePaymentMethod = scanner.nextLine();

        if ("1".equals(choicePaymentMethod)) {
            this.moneyAcceptor = new BanknoteAcceptor();
            System.out.println("Выбран метод оплаты: Наличные");
        } else if ("2".equals(choicePaymentMethod)) {
            this.moneyAcceptor = new CardAcceptor();
            System.out.println("Выбран метод оплаты: Карта");
        } else {
            System.out.println("Неверный ввод. По умолчанию используется метод оплаты: Наличные");
            this.moneyAcceptor = new BanknoteAcceptor();
        }
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Монет на сумму: " + moneyAcceptor.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyAcceptor.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole();

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        if ("a".equalsIgnoreCase(action)) {
            moneyAcceptor.acceptMoney();
            return;
        }


        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    moneyAcceptor.deductAmount(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
                print("Недопустимая буква. Попрбуйте еще раз.");
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
