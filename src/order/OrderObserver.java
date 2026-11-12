package order;

public interface OrderObserver {
    void onStatusChanged(Order order, OrderStatus previous, OrderStatus current);
}
