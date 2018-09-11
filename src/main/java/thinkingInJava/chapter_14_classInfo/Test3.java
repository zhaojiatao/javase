package thinkingInJava.chapter_14_classInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/9/11
 *
 * 在类型转换之前先做检查，如果贸然强制转换，可能会抛出ClassCastException异常；
 * 学习使用instance of 和isInstance()进行类型检查；
 * 主要区别就是instance of是编译器进行类型检查；
 * 而 isInstance方法是运行期，动态进行类型检查，可用于反射、泛型中；
 *
 */

public class Test3 {
    public static boolean DynamicEqual(Object fatherObj,Object sonObj){
         return fatherObj.getClass().isInstance(sonObj); // pass
        // return sonObj.getClass().isInstance(fatherObj);
        // return sonObj instanceof Father; // pass
        // return sonObj instanceof (fatherObj.getClass()); //error
    }

    public static void main(String[] args){
        //instance of 编译器类型检查
        Father father = new Father();
        Son son = new Son();

        System.out.println(son instanceof Son); // true
        System.out.println(son instanceof Father); // true
        System.out.println(son instanceof Object); // true
        System.out.println(null instanceof Object); // false
        System.out.println();

        //运行时动态类型检查(括号里的是子类)
        System.out.println(Son.class.isInstance(son)); // true
        //很明显是错误的，但编译是可以通过的
        System.out.println(Integer.class.isInstance(son));//false
        System.out.println(Father.class.isInstance(son)); // true
        System.out.println(Object.class.isInstance(son)); // true
        System.out.println(Object.class.isInstance(null)); // false
        System.out.println();


        //different using
        System.out.println(DynamicEqual(father, son));
    }


}

class Father{}

class Son extends Father{}

