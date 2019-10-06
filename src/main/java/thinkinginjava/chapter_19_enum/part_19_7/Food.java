package thinkinginjava.chapter_19_enum.part_19_7;

import thinkinginjava.chapter_19_enum.part_19_6.Enums;

/**
 * @author zhaojiatao
 * @date 2019-10-06
 *
 * 让枚举实现接口 可以达到让枚举分类的目的
 * 对于枚举而言，实现接口使其子类化的唯一办法
 */
public interface Food {

    enum Appetizer implements Food{
        SALAD,SOUP,SPRING_ROLLS;
    }

    enum MainCourse implements Food{
        LASAGNE,BURRITO,PAD_THAI,LENTILS,HUMMOUS,VINDALOO;
    }

    enum Dessert implements Food{
        TIRAMISU,GELATO,BLACK_FOREST_CAKE,FRUIT,CREME_CARAMEL;
    }

    enum Coffee implements Food{
        BLACK_COFFEE,DECAF_COFFEE,ESPRESSO,LATTE,CAPPUCCINO,TEA,HERB_TEA;
    }


}

/**
 * 所有实现了Food接口的枚举都可以向上转型为Food
 */
class TypeOfFood{
    public static void main(String[] args) {
        Food food= Food.Appetizer.SALAD;
        food= Food.MainCourse.LASAGNE;
        food= Food.Dessert.GELATO;
        food= Food.Coffee.CAPPUCCINO;

    }
}

/**
 * 枚举的枚举
 */
enum Course{
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    ;
    private Food[] values;

    Course(Class<? extends Food> kind) {
        this.values = kind.getEnumConstants();
    }

    public Food randomSelection(){
        return Enums.random(values);
    }
}

class Meal{
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            for (Course course:Course.values()){
                Food food=course.randomSelection();
                System.out.println(food);
            }
            System.out.println("---------");
        }
    }
}

/**
 * 将一个enum嵌套在另一个enum内，这种方式可以让代码结构更清晰
 */
enum SecurityCategory{

    STOCK(Security.Stock.class),
    BOND(Security.Bond.class),

    ;

    Security[] values;

    SecurityCategory(Class<? extends Security> kind) {
        this.values = kind.getEnumConstants();
    }

    interface Security{
        enum Stock implements Security{
            SHORT,LONG,MARGIN
        }
        enum Bond implements Security{
            MUNICIPAL,JUNK
        }
    }

    public Security randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            SecurityCategory category=Enums.random(SecurityCategory.class);
            System.out.println(category+": "+category.randomSelection());
            
        }
    }




}

















