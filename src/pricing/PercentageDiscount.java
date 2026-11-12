package pricing;

public class PercentageDiscount implements DiscountStrategy {
    private static final int FULL_PERCENT = 100;

    private final int percent;

    public PercentageDiscount(int percent) {
        if (percent < 0 || percent > FULL_PERCENT) {
            throw new IllegalArgumentException("Discount percent must be between 0 and " + FULL_PERCENT);
        }
        this.percent = percent;
    }

    @Override
    public double apply(double subtotal) {
        return subtotal * (FULL_PERCENT - percent) / FULL_PERCENT;
    }

    @Override
    public String getName() {
        return percent + "%";
    }
}