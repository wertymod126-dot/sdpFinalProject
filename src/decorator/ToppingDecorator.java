package decorator;

import factory.meal.Meal;

public abstract class ToppingDecorator implements Meal {
    protected final Meal meal;

    protected ToppingDecorator(Meal meal) {
        if (meal == null) {
            throw new IllegalArgumentException("Meal to decorate must not be null");
        }
        this.meal = meal;
    }

    @Override
    public String getDescription() {
        return meal.getDescription() + " + " + getToppingName();
    }

    @Override
    public double getCost() {
        return meal.getCost() + getToppingPrice();
    }

    protected abstract String getToppingName();

    protected abstract double getToppingPrice();
}