package strategy;

public interface DiscountStrategy {
    double apply(double subtotal);

    String getName();
}