package pricing;

public interface DiscountStrategy {
    double apply(double subtotal);

    String getName();
}