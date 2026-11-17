package observer;

import builder.Order;
import builder.OrderStatus;

public interface OrderObserver {
    void onStatusChanged(Order order, OrderStatus previous, OrderStatus current);
}
