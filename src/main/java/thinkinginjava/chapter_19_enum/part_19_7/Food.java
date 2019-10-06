package thinkinginjava.chapter_19_enum.part_19_7;

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
