package facade;

import factory.meal.Meal;
import factory.MealFactory;
import observer.listener.CustomerNotifier;
import observer.listener.KitchenDisplay;
import builder.Order;
import builder.OrderBuilder;
import builder.OrderStatus;
import builder.OrderType;
import strategy.DiscountStrategy;
import strategy.discount.NoDiscount;
import strategy.discount.PercentageDiscount;
import decorator.topping.Bacon;
import decorator.topping.ExtraCheese;
import decorator.topping.Mushrooms;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RestaurantFacade {
    private final MealFactory mealFactory = new MealFactory();
    private final KitchenDisplay kitchenDisplay = new KitchenDisplay();
    private final CustomerNotifier customerNotifier = new CustomerNotifier();
    private final Map<Integer, Order> orders = new LinkedHashMap<>();

    public Order placeOrder(String customer, OrderType type, String detail,
                            String notes, List<String> items) {
        OrderBuilder builder = new OrderBuilder().forCustomer(customer).withNotes(notes);
        switch (type) {
            case DINE_IN:
                try {
                    builder.dineIn(Integer.parseInt(detail.trim()));
                } catch (NumberFormatException | NullPointerException e) {
                    throw new IllegalArgumentException("Table number must be a number");
                }
                break;
            case DELIVERY:
                builder.delivery(detail);
                break;
            case TAKEAWAY:
                builder.takeaway();
                break;
        }
        for (String item : items) {
            builder.addMeal(createMeal(item));
        }
        Order order = builder.build();
        order.addObserver(kitchenDisplay);
        order.addObserver(customerNotifier);
        orders.put(order.getId(), order);
        order.announcePlaced();
        return order;
    }

    public void advanceStatus(int orderId) {
        find(orderId).advance();
    }

    public String checkout(int orderId) {
        return checkout(orderId, null);
    }

    public String checkout(int orderId, String discountCode) {
        Order order = find(orderId);
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Order #" + orderId + " is not delivered yet ("
                    + order.getStatus() + ")");
        }
        DiscountStrategy discount = discountFor(discountCode);
        double subtotal = order.getSubtotal();
        double total = discount.apply(subtotal);
        return order + String.format("%n  Discount (%s): -%.2f%n  TOTAL: %.2f",
                discount.getName(), Math.abs(subtotal - total), total);
    }

    public Order getOrder(int orderId) {
        return find(orderId);
    }

    public List<Order> getOrders() {
        return List.copyOf(orders.values());
    }

    // ---- hidden details ----

    private Meal createMeal(String item) {
        String[] parts = item.split("\\+");
        Meal meal = mealFactory.createMeal(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            switch (parts[i].trim().toLowerCase()) {
                case "cheese":
                    meal = new ExtraCheese(meal);
                    break;
                case "mushrooms":
                    meal = new Mushrooms(meal);
                    break;
                case "bacon":
                    meal = new Bacon(meal);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown topping: " + parts[i]);
            }
        }
        return meal;
    }

    private DiscountStrategy discountFor(String code) {
        if (code == null || code.isBlank()) {
            return new NoDiscount();
        }
        switch (code.trim().toUpperCase()) {
            case "STUDENT10":
                return new PercentageDiscount(10);
            case "HAPPY20":
                return new PercentageDiscount(20);
            default:
                throw new IllegalArgumentException("Unknown discount code: " + code);
        }
    }

    private Order find(int orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order with id " + orderId);
        }
        return order;
    }
}
