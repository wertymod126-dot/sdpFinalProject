package topping;

import meal.Meal;

public class ExtraCheese extends ToppingDecorator {
    private static final double PRICE = 1.00;

    public ExtraCheese(Meal meal) {
        super(meal);
    }

    @Override
    protected String getToppingName() {
        return "Cheese";
    }

    @Override
    protected double getToppingPrice() {
        return PRICE;
    }
}