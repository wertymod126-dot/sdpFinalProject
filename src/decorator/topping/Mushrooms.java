package decorator.topping;

import decorator.ToppingDecorator;
import factory.meal.Meal;

public class Mushrooms extends ToppingDecorator {
    private static final double PRICE = 1.50;

    public Mushrooms(Meal meal) {
        super(meal);
    }

    @Override
    protected String getToppingName() {
        return "Mushrooms";
    }

    @Override
    protected double getToppingPrice() {
        return PRICE;
    }
}