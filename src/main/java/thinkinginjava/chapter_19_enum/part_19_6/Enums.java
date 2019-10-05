package thinkinginjava.chapter_19_enum.part_19_6;

import java.util.Random;

/**
 * @author zhaojiatao
 * @date 2019-10-05
 * 演示从枚举中随机选择
 * Enum只是一个相当短小的类，但是可以消除很多重复的代码
 */
public class Enums {


    private static Random rand=new Random(47);

    /**
     *
     * @param ec 将Class<T> ec作为参数，我们就可以利用class对象得到enum实例的数组了
     * @param <T>  <T extends Enum<T>> 表示T是一个enum实例
     * @return
     */
    public static <T extends Enum<T>> T random(Class<T> ec){
        return random(ec.getEnumConstants());
    }

    /**
     * 重载后的random()方法只需要使用T[]作为参数，因为它并不会调用Enum上的任何操作
     * @param values
     * @param <T>
     * @return
     */
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