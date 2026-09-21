import java.util.ArrayList;
import java.util.Scanner;

public class Stock {

    String symbol;
    double price;
    int quantity;

    // Constructor
    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = 0;
    }

    static ArrayList<Stock> stocks = new ArrayList<>();
    static double balance = 100000;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Initial stocks
        stocks.add(new Stock("TCS", 3500));
        stocks.add(new Stock("INFY", 1800));
        stocks.add(new Stock("RELIANCE", 2900));
        stocks.add(new Stock("HDFC", 1700));

        int choice = 0; // ✅ FIX: initialize variable

        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. Display Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            // ✅ Input validation
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                sc.next();
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock(sc);
                    break;

                case 3:
                    sellStock(sc);
                    break;

                case 4:
                    portfolio();
                    break;

                case 5:
                    System.out.println("Balance: ₹" + balance);
                    break;

                case 6:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }

    static void displayMarket() {

        System.out.println("\n----- MARKET -----");

        for (Stock s : stocks) {
            System.out.println(s.symbol + " : ₹" + s.price);
        }
    }

    static void buyStock(Scanner sc) {

        displayMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next();

        System.out.print("Enter quantity: ");

        // ✅ Validate quantity
        if (!sc.hasNextInt()) {
            System.out.println("Invalid quantity!");
            sc.next();
            return;
        }

        int quantity = sc.nextInt();

        for (Stock s : stocks) {

            if (s.symbol.equalsIgnoreCase(symbol)) {

                double cost = s.price * quantity;

                if (cost <= balance) {

                    balance -= cost;
                    s.quantity += quantity;

                    System.out.println("Bought " + quantity + " shares of " + s.symbol);

                } else {
                    System.out.println("Insufficient balance!");
                }

                return;
            }
        }

        System.out.println("Stock not found!");
    }

    static void sellStock(Scanner sc) {

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next();

        System.out.print("Enter quantity: ");

        // ✅ Validate quantity
        if (!sc.hasNextInt()) {
            System.out.println("Invalid quantity!");
            sc.next();
            return;
        }

        int quantity = sc.nextInt();

        for (Stock s : stocks) {

            if (s.symbol.equalsIgnoreCase(symbol)) {

                if (quantity <= s.quantity) {

                    balance += s.price * quantity;
                    s.quantity -= quantity;

                    System.out.println("Sold " + quantity + " shares of " + s.symbol);

                } else {
                    System.out.println("You don't own enough shares!");
                }

                return;
            }
        }

        System.out.println("Stock not found!");
    }

    static void portfolio() {

        System.out.println("\n----- PORTFOLIO -----");

        boolean found = false;

        for (Stock s : stocks) {

            if (s.quantity > 0) {

                double value = s.quantity * s.price;

                System.out.println(
                    s.symbol + " | Quantity: " +
                    s.quantity + " | Value: ₹" + value
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("Portfolio is empty.");
        }
    }
