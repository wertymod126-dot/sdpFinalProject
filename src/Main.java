import facade.RestaurantFacade;
import builder.OrderType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final RestaurantFacade restaurant = new RestaurantFacade();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("=== Restaurant Order System ===");
        while (true) {
            System.out.println("\n1) Run demo scenario");
            System.out.println("2) Place order");
            System.out.println("3) Advance order status");
            System.out.println("4) Checkout");
            System.out.println("5) List orders");
            System.out.println("0) Exit");
            System.out.print("> ");
            if (!in.hasNextLine()) return;
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        runDemo();
                        break;
                    case "2":
                        placeOrder(in);
                        break;
                    case "3":
                        restaurant.advanceStatus(askInt(in, "Order id: "));
                        break;
                    case "4":
                        int id = askInt(in, "Order id: ");
                        System.out.print("Discount code (STUDENT10 / HAPPY20, empty = none): ");
                        System.out.println(restaurant.checkout(id, in.nextLine()));
                        break;
                    case "5":
                        restaurant.getOrders().forEach(o -> System.out.println(o + "\n"));
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Unknown option.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void placeOrder(Scanner in) {
        System.out.print("Customer name: ");
        String name = in.nextLine();
        System.out.print("Type (dine_in / takeaway / delivery): ");
        OrderType type;
        try {
            type = OrderType.valueOf(in.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown order type");
        }
        String detail = null;
        if (type == OrderType.DINE_IN) {
            System.out.print("Table number: ");
            detail = in.nextLine();
        } else if (type == OrderType.DELIVERY) {
            System.out.print("Address: ");
            detail = in.nextLine();
        }
        System.out.println("Items: pizza, burger, salad, drink (toppings: cheese, mushrooms, bacon)");
        System.out.print("Comma-separated, e.g. pizza+cheese, drink: ");
        List<String> items = Arrays.stream(in.nextLine().split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).toList();
        System.out.print("Notes (optional): ");
        String notes = in.nextLine();
        restaurant.placeOrder(name, type, detail, notes.isBlank() ? null : notes, items);
    }

    private static int askInt(Scanner in, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a number");
        }
    }

    private static void runDemo() {
        System.out.println("\n--- 1. Alice orders dine-in (Factory + Builder + Decorator) ---");
        var alice = restaurant.placeOrder("Alice", OrderType.DINE_IN, "5",
                "No onions", List.of("pizza+cheese+mushrooms", "drink"));

        System.out.println("\n--- 2. Bob orders delivery ---");
        var bob = restaurant.placeOrder("Bob", OrderType.DELIVERY, "12 Main Street",
                null, List.of("burger+bacon", "salad"));

        System.out.println("\n--- 3. Alice's order goes through the kitchen (Observer) ---");
        for (int i = 0; i < 3; i++) {
            restaurant.advanceStatus(alice.getId());
        }

        System.out.println("\n--- 4. Alice checks out with a discount (Strategy) ---");
        System.out.println(restaurant.checkout(alice.getId(), "STUDENT10"));

        System.out.println("\n--- 5. Error handling ---");
        try {
            restaurant.checkout(bob.getId());
        } catch (IllegalStateException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        try {
            restaurant.placeOrder("Carol", OrderType.TAKEAWAY, null, null, List.of("sushi"));
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        System.out.println("\n--- 6. Bob's order finishes ---");
        for (int i = 0; i < 3; i++) {
            restaurant.advanceStatus(bob.getId());
        }
        System.out.println(restaurant.checkout(bob.getId()));
    }
}
