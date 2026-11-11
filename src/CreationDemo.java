import meal.Meal;
import meal.MealFactory;
import meal.MealType;
import order.Order;
import order.OrderBuilder;

/** Small demo of the Factory and Builder patterns working together. */
public class CreationDemo {
    public static void main(String[] args) {
        MealFactory factory = new MealFactory();

        Meal pizza = factory.createMeal(MealType.PIZZA);
        Meal salad = factory.createMeal("salad");
        Meal drink = factory.createMeal(MealType.DRINK);

        Order dineIn = new OrderBuilder()
                .forCustomer("Alice")
                .dineIn(5)
                .addMeal(pizza)
                .addMeal(drink)
                .withNotes("No onions")
                .build();
        System.out.println(dineIn);
        System.out.println();

        Order delivery = new OrderBuilder()
                .forCustomer("Bob")
                .delivery("12 Main Street")
                .addMeal(factory.createMeal(MealType.BURGER))
                .addMeal(salad)
                .build();
        System.out.println(delivery);
        System.out.println();

        try {
            new OrderBuilder().forCustomer("Carol").takeaway().build();
        } catch (IllegalStateException e) {
            System.out.println("Invalid order rejected: " + e.getMessage());
        }

        try {
            factory.createMeal("sushi");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid meal rejected: " + e.getMessage());
        }
    }
}
