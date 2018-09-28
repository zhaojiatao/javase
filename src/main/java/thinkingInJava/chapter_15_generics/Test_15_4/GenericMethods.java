package thinkingInJava.chapter_15_generics.Test_15_4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/9/27
 *
 * 范型方法
 *
 */
public class GenericMethods {
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }

    //范型方法的使用，当调用f()方法传入基本类型时，自动打包机制会将基本类型包装为对应的包装类型
    @Test
    public void test0(){
        GenericMethods genericMethods=new GenericMethods();
        genericMethods.f(" ");
        genericMethods.f(1);
        genericMethods.f(1.0);
        genericMethods.f(1.0F);
        genericMethods.f('c');
        genericMethods.f(genericMethods);
    }



    //学习范型的类型参数推断机制，在jdk1.6以后编译器更加智能，可以自动推断范型方法的参数
    @Test
    public void test01(){
        List<String> list=new ArrayList<>();
        Collections.addAll(list,"a","b");
        System.out.println(list.toString());

        List<String> list2=new ArrayList<String>();
        Collections.addAll(list2,"c","d");
        System.out.println(list2.toString());

    }

    //与java标准库中的java.util.Arrays.asList()方法功能相同
    public static <T> List<T> makeList(T... args){
        List<T> result=new ArrayList<>();
        for (T item:args){
            result.add(item);
        }
        return result;
    }

    //可变参数与泛型列表可以共存
    @Test
    public void test02(){
        List<String> ls=makeList("A");
        System.out.println(ls);

        ls=makeList("A","B","C");
        System.out.println(ls);

        ls=makeList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        System.out.println(ls);



    }







}
