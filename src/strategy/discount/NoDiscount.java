package strategy.discount;

import strategy.DiscountStrategy;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double apply(double subtotal) {
        return subtotal;
    }

    @Override
    public String getName() {
        return "none";
    }
}