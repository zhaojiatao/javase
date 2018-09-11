package thinkingInJava.chapter_14_classInfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/9/11
 *
 * 在类型转换之前先做检查，如果贸然强制转换，可能会抛出ClassCastException异常；
 * 学习使用instance of 和isInstance()进行类型检查
 *
 */
public class Test3 {
    class A{
        String name;
        String getName(){
            return name;
        }
    }
    class B extends A{
        String name="B";
        String getName(){
            return name;
        }
    }
    class C extends A{
        String name="C";
        String getName(){
            return name;
        }
    }


    /**
     * obj instanceof class
     也就是说这个对象是不是这种类型，
     1.一个对象是本身类的一个对象
     2.一个对象是本身类父类（父类的父类）和接口（接口的接口）的一个对象
     3.所有对象都是Object
     4.凡是null有关的都是false  null.instanceof(class)
     */
    @Test
    public void test01(){
        //当new了一个对象，将这个对象的值赋值给一个引用时，需要进行类型检查；
        //但是这种方法每次均需要用instanceof来检查，很不方便
        List<A> list=new ArrayList<A>();
        list.add(new B());
        list.add(new C());
        for(A x:list){
            if(x instanceof B){
                B bb=(B)x;
                System.out.println(bb.name);
            }
            if(x instanceof C){
                C cc=(C)x;
                System.out.println(cc.name);
            }
        }
    }

    /**
     * class.inInstance(obj)

     这个对象能不能被转化为这个类

     1.一个对象是本身类的一个对象

     2.一个对象能被转化为本身类所继承类（父类的父类等）和实现的接口（接口的父接口）强转

     3.所有对象都能被Object的强转

     4.凡是null有关的都是false   class.inInstance(null)
     */
    @Test
    public void test02(){
        List<A> list=new ArrayList<A>();
        B b=new B();
        list.add(b);
        C c=new C();
        list.add(c);
        for(A x:list){
            if(A.class.isInstance(x)){
                System.out.println(x.getName());
            }
        }

    }




}
