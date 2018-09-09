package thinkingInJava.chapter_14_classInfo;

/**
 * @author zhaojiatao
 * @date 2018/9/9
 * 练习8：写一个方法，令它接受任意对象作为参数，并能够递归打印出该对象所在的继承体系中的所有类
 */
class A{

}

class B extends A{

}

class C extends B{

}

class D extends C{

}

public class Test_2_1 {
    private static void printsuperclass(Object a) {
        Class className=a.getClass();
        System.out.println(className.getName());
        while(!className.getName().equals("java.lang.Object")){
            className=className.getSuperclass();
            System.out.println(className.getName());
        }
    }
    public static void main(String[] args) {
        printsuperclass(new D());
    }


}