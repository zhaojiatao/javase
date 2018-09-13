package thinkingInJava.chapter_14_classInfo;

import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zhaojiatao
 * @date 2018/9/13
 */
class Student {

    //---------------构造方法-------------------
    //（默认的构造方法）
    Student(String str){
        System.out.println("(默认)的构造方法 s = " + str);
    }

    //无参构造方法
    public Student(){
        System.out.println("调用了公有、无参构造方法执行了。。。");
    }

    //有一个参数的构造方法
    public Student(char name){
        System.out.println("姓名：" + name);
    }

    //有多个参数的构造方法
    public Student(String name ,int age){
        System.out.println("姓名："+name+"年龄："+ age);//这的执行效率有问题，以后解决。
    }

    //受保护的构造方法
    protected Student(boolean n){
        System.out.println("受保护的构造方法 n = " + n);
    }

    //私有构造方法
    private Student(int age){
        System.out.println("私有的构造方法   年龄："+ age);
    }

}




public class Test6 {
    /*
     * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
     *
     * 1.获取构造方法：
     * 		1).批量的方法：
     * 			public Constructor[] getConstructors()：所有"公有的"构造方法
                public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

     * 		2).获取单个的方法，并调用：
     * 			public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
     * 			public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
     *
     * 			调用构造方法：
     * 			Constructor-->newInstance(Object... initargs)
     */



    //通过反射获取构造方法并使用
    @Test
    public void test01() {

        //1.加载Class对象
        Class clazz = null;
        try {
            clazz = Class.forName("thinkingInJava.chapter_14_classInfo.Student");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2.获取所有公有构造方法
            System.out.println("**********************所有公有构造方法*********************************");
            //Returns an array containing Constructor objects reflecting all the public constructors of the class represented by this Class object.
            Constructor[] conArray = clazz.getConstructors();
            for(Constructor c : conArray){
                System.out.println(c);
            }

            System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
            //Returns an array of Constructor objects reflecting all the constructors declared by the class represented by this Class object.
            conArray = clazz.getDeclaredConstructors();
            for(Constructor c : conArray){
                System.out.println(c);
            }

            System.out.println("*****************获取公有、无参的构造方法*******************************");
            Constructor con = null;
            try {
                con = clazz.getConstructor(null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
            //2>、返回的是描述这个无参构造函数的类对象。

            System.out.println("con = " + con);
            //调用构造方法
            Object obj = null;
            try {
                obj = con.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            System.out.println("obj = " + obj);
            Student stu = (Student)obj;
            System.out.println("使用无参构造方法创建的Student对象的实例：stu="+stu.toString());

            System.out.println("******************获取私有构造方法，并调用*******************************");
            try {
                con = clazz.getDeclaredConstructor(char.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            System.out.println(con);
            //调用构造方法
            con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
            try {
                obj = con.newInstance('男');
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            System.out.println("obj = " + obj);
            stu = (Student)obj;
            System.out.println("使用私有构造方法创建的Student对象的实例：stu="+stu.toString());

    }







}




