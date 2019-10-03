package thinkinginjava.chapter_19_enum.part_19_1;
import static thinkinginjava.chapter_19_enum.part_19_1.Spiciness.*;

/**
 * @author zhaojiatao
 * @date 2019-10-03
 * 这个类主要演示只要引入了static的thinkinginjava.chapter_19_enum.part_19_1.Spiciness.*
 * 使用static import 能够将enum实例的标志符带入当前的命名空间，所以无需再用enum类型来修饰enum实例。
 */

enum Spiciness {
    NOT, MILD, MEDIUM, HOT, FLAMING

}

public class Burrito {
    Spiciness degree;

    public Burrito(Spiciness degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Burrito is "+degree;
    }

    public static void main(String[] args) {
        System.out.println(new Burrito(NOT));
        System.out.println(new Burrito(MEDIUM));
        System.out.println(new Burrito(HOT));
    }
}



