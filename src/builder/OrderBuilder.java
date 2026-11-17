package builder;

import factory.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    String customerName;
    OrderType type = OrderType.DINE_IN;
    Integer tableNumber;
    String deliveryAddress;
    String notes;
    final List<Meal> meals = new ArrayList<>();

    public OrderBuilder forCustomer(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public OrderBuilder dineIn(int tableNumber) {
        this.type = OrderType.DINE_IN;
        this.tableNumber = tableNumber;
        this.deliveryAddress = null;
        return this;
    }

    public OrderBuilder takeaway() {
        this.type = OrderType.TAKEAWAY;
        this.tableNumber = null;
        this.deliveryAddress = null;
        return this;
    }

    public OrderBuilder delivery(String address) {
        this.type = OrderType.DELIVERY;
        this.deliveryAddress = address;
        this.tableNumber = null;
        return this;
    }

    public OrderBuilder addMeal(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal must not be null");
        }
        meals.add(meal);
        return this;
    }

    public OrderBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Order build() {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalStateException("Order needs a customer name");
        }
        if (meals.isEmpty()) {
            throw new IllegalStateException("Order needs at least one meal");
        }
        if (type == OrderType.DINE_IN && (tableNumber == null || tableNumber <= 0)) {
            throw new IllegalStateException("Dine-in order needs a table number");
        }
        if (type == OrderType.DELIVERY && (deliveryAddress == null || deliveryAddress.isBlank())) {
            throw new IllegalStateException("Delivery order needs an address");
        }
        return new Order(this);
    }
}
