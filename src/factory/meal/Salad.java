package factory.meal;

public class Salad implements Meal {
    @Override
    public String getDescription() {
        return "Salad";
    }

    @Override
    public double getCost() {
        return 4.50;
    }
}
