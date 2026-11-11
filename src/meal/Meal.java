package meal;

/**
 * Common interface for everything that can be ordered.
 * Concrete meals implement it, and topping decorators wrap it.
 */
public interface Meal {
    String getDescription();

    double getCost();
}
