package thinkinginjava.chapter_19_enum.part_19_6;

import java.util.Random;

/**
 * @author zhaojiatao
 * @date 2019-10-05
 */
public class Enums {


    private static Random rand=new Random(47);

    public static <T extends Enum<T>> T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values){
        T value = values[rand.nextInt(values.length)];
        return value;
    }

}
enum Activity{
    SITTING,LYING,STANDING,HOPING,RUNNING,DODGING,JUMPING,FALLING,FLYING,;
}
class RandomTest{
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(Enums.random(Activity.class));
        }
    }
}