package thinkinginjava.chapter_13_string;

import org.junit.Test;
import java.util.Arrays;

/**
 * @author zhaojiatao
 * @date 2018/9/8
 */
public class Test6_RegularExpression {

    //正则匹配正负数
    @Test
    public void IntegerMatch(){
        System.out.println("-1234".matches("-?\\d+"));
        System.out.println("5678".matches("-?\\d+"));
        System.out.println("+978".matches("-?\\d+"));
        System.out.println("+911".matches("(-|\\+)?\\d+"));
    }



    @Test
    public void Splitting(){
        String str="Then , when you have found the shrubbery , you must cut down the mightiest tree in the forest ... with ... a herring!";

        System.out.println(Arrays.toString(str.split(" ")));
        System.out.println(Arrays.toString(str.split("\\W+")));
        System.out.println(Arrays.toString(str.split("n\\W+")));

    }


    @Test
    public void Replacing(){
        String str="Then , when you have found the shrubbery , you must cut down the mightiest tree in the forest ... with ... a herring!";
        System.out.println(str.replaceFirst("f\\w+","located"));
        System.out.println(str.replaceAll("shrubbery|tree|herring","banana"));

    }








}
