package pattern.builder;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 *
 * 当一些基本的部件不会变，但其组合经常变化的时候，可以考虑使用建造者模式
 */
public class BuilderPatternDemo {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();

        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total Cost: " +vegMeal.getCost());

        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total Cost: " +nonVegMeal.getCost());

        StringBuilder stringBuilder=new StringBuilder();

    }
}
