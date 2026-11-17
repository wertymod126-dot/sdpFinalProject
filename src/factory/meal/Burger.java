package factory.meal;

public class Burger implements Meal {
    @Override
    public String getDescription() {
        return "Burger";
    }

    @Override
    public double getCost() {
        return 6.00;
    }
}
