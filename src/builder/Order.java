package builder;

import observer.OrderObserver;
import factory.meal.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final List<OrderObserver> observers = new ArrayList<>();

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

    public void addObserver(OrderObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void announcePlaced() {
        notifyObservers(null, status);
    }

    public void advance() {
        OrderStatus[] all = OrderStatus.values();
        if (status.ordinal() == all.length - 1) {
            throw new IllegalStateException("Order #" + id + " is already " + status);
        }
        setStatus(all[status.ordinal() + 1]);
    }

    public void setStatus(OrderStatus newStatus) {
        if (newStatus == null || newStatus.ordinal() != status.ordinal() + 1) {
            throw new IllegalStateException(
                    "Order #" + id + " cannot go from " + status + " to " + newStatus);
        }
        OrderStatus previous = this.status;
        this.status = newStatus;
        notifyObservers(previous, newStatus);
    }

    private void notifyObservers(OrderStatus previous, OrderStatus current) {
        for (OrderObserver observer : new ArrayList<>(observers)) {
            observer.onStatusChanged(this, previous, current);
        }
    }

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
