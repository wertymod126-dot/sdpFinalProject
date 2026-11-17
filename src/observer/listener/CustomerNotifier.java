package observer.listener;

import builder.Order;
import observer.OrderObserver;
import builder.OrderStatus;
import builder.OrderType;

public class CustomerNotifier implements OrderObserver {
    @Override
    public void onStatusChanged(Order order, OrderStatus previous, OrderStatus current) {
        String prefix = "[NOTIFY -> " + order.getCustomerName() + "] ";
        switch (current) {
            case PLACED:
                System.out.println(prefix + "Thanks! Your order #" + order.getId() + " was received.");
                break;
            case COOKING:
                System.out.println(prefix + "Your food is being prepared.");
                break;
            case READY:
                String how = order.getType() == OrderType.DELIVERY ? "is waiting for the courier."
                        : order.getType() == OrderType.TAKEAWAY ? "is ready for pickup."
                        : "is ready and will be brought to your table.";
                System.out.println(prefix + "Your order " + how);
                break;
            case DELIVERED:
                System.out.println(prefix + "Enjoy your meal! Please proceed to checkout.");
                break;
        }
    }
}
