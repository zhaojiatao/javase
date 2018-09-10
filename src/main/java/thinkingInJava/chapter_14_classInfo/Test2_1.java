package thinkingInJava.chapter_14_classInfo;

import java.util.Random;

/**
 * @author zhaojiatao
 * @date 2018/9/10
 *
 * 除了使用class的forName方法获取类的Class对象的引用外，还可以使用类字面常量；
 * 这种方式不仅简单，且更安全，因为会在类编译期就进行安全检查；
 * 注意，使用.class的方式，获取类的Class对象的引用，不会像forName()那样初始化类的Class对象；
 * .class的方式仅仅是获取了Class对象的引用，只有到了类真正被初始化结束后，Class对象才真正被初始化；即：
 * 只有在对静态方法或非常数静态域进行首次引用时才执行初始化；
 *
 */
public class Test2_1 {
    public static Random rand=new Random(47);

    public static void main(String[] args) throws ClassNotFoundException {
        Class initable=Initable.class;
        System.out.println("这个时候仅仅是获取到了Initable类的Class对象引用，Class对象还没初始化");
        //对于static final值是编译期常量，则该值无需对类初始化就可以被读取；
        System.out.println(Initable.staticFinal);
        //非编译期常量，即使被static和final修饰，也必须先初始化类Class对象，才能读取；
        System.out.println(Initable.staticFinal2);

        //如果一个static域不是final的，那么在对它访问时，总是要求在它被读取之前，先进行链接(为这个域分配存储空间)
        //以及初始化(初始化该存储空间)
        System.out.println(Initable2.staticNonFinal);

        //使用forName方法，就会初始化类
        Class initable3=Class.forName("thinkingInJava.chapter_14_classInfo.Initable3");
        System.out.println(Initable3.staticNonFinal);



    }

}


class Initable{
    static final int staticFinal=47;
    static final int staticFinal2=(int)(1+Math.random()*(10-1+1));
    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2{
    static int staticNonFinal=147;
    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3{
    static int staticNonFinal=74;
    static{
        System.out.println("Initializing Initable3");
    }
}

