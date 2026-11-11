package order;

import meal.Meal;

import java.util.Collections;
import java.util.List;

/**
 * An order placed by a customer. Instances are created only through
 * OrderBuilder, so an Order is always complete and valid.
 */
public class Order {
    private static int nextId = 1;

    private final int id;
    private final String customerName;
    private final OrderType type;
    private final Integer tableNumber;
    private final String deliveryAddress;
    private final String notes;
    private final List<Meal> meals;
    private OrderStatus status;

    Order(OrderBuilder builder) {
        this.id = nextId++;
        this.customerName = builder.customerName;
        this.type = builder.type;
        this.tableNumber = builder.tableNumber;
        this.deliveryAddress = builder.deliveryAddress;
        this.notes = builder.notes;
        this.meals = List.copyOf(builder.meals);
        this.status = OrderStatus.PLACED;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderType getType() {
        return type;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public List<Meal> getMeals() {
        return Collections.unmodifiableList(meals);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /** Sum of all meal prices, before any discount is applied. */
    public double getSubtotal() {
        double sum = 0;
        for (Meal meal : meals) {
            sum += meal.getCost();
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(id).append(" for ").append(customerName);
        sb.append(" [").append(type).append(", ").append(status).append("]\n");
        if (tableNumber != null) {
            sb.append("  Table: ").append(tableNumber).append("\n");
        }
        if (deliveryAddress != null) {
            sb.append("  Address: ").append(deliveryAddress).append("\n");
        }
        for (Meal meal : meals) {
            sb.append(String.format("  - %-30s %6.2f%n", meal.getDescription(), meal.getCost()));
        }
        if (notes != null) {
            sb.append("  Notes: ").append(notes).append("\n");
        }
        sb.append(String.format("  Subtotal: %.2f", getSubtotal()));
        return sb.toString();
    }
}
