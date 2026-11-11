package meal;

public class Drink implements Meal {
    @Override
    public String getDescription() {
        return "Drink";
    }

    @Override
    public double getCost() {
        return 2.00;
    }
}
