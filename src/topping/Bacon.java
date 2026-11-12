package topping;

import meal.Meal;

public class Bacon extends ToppingDecorator {
    private static final double PRICE = 2.00;

    public Bacon(Meal meal) {
        super(meal);
    }

    @Override
    protected String getToppingName() {
        return "Bacon";
    }

    @Override
    protected double getToppingPrice() {
        return PRICE;
    }
}
