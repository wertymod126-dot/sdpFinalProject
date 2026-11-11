package meal;

public class Pizza implements Meal {
    @Override
    public String getDescription() {
        return "Pizza";
    }

    @Override
    public double getCost() {
        return 8.50;
    }
}
