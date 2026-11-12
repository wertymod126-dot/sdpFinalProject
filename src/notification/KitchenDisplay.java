package notification;

import order.Order;
import order.OrderObserver;
import order.OrderStatus;

public class KitchenDisplay implements OrderObserver {
    @Override
    public void onStatusChanged(Order order, OrderStatus previous, OrderStatus current) {
        String prefix = "[KITCHEN] Order #" + order.getId() + ": ";
        switch (current) {
            case PLACED:
                StringBuilder items = new StringBuilder();
                for (var meal : order.getMeals()) {
                    if (items.length() > 0) items.append(", ");
                    items.append(meal.getDescription());
                }
                System.out.println(prefix + "NEW TICKET -> " + items
                        + (order.getNotes() != null ? " (note: " + order.getNotes() + ")" : ""));
                break;
            case COOKING:
                System.out.println(prefix + "cooking started");
                break;
            case READY:
                System.out.println(prefix + "ready for pickup at the pass");
                break;
            case DELIVERED:
                System.out.println(prefix + "ticket cleared from screen");
                break;
        }
    }
}
