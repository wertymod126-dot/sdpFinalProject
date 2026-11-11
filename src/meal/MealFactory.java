package meal;

/**
 * Factory pattern: the only place that knows which concrete class
 * belongs to which meal type. Client code asks for a MealType and
 * works with the Meal interface.
 */
public class MealFactory {

    public Meal createMeal(MealType type) {
        if (type == null) {
            throw new IllegalArgumentException("Meal type must not be null");
        }
        switch (type) {
            case PIZZA:
                return new Pizza();
            case BURGER:
                return new Burger();
            case SALAD:
                return new Salad();
            case DRINK:
                return new Drink();
            default:
                throw new IllegalArgumentException("Unknown meal type: " + type);
        }
    }

    /** Convenience overload for console input such as "pizza". */
    public Meal createMeal(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Meal name must not be empty");
        }
        try {
            return createMeal(MealType.valueOf(name.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown meal: " + name);
        }
    }
}
