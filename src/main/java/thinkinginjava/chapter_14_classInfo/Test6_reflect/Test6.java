package thinkinginjava.chapter_14_classInfo.Test6_reflect;

import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author zhaojiatao
 * @date 2018/9/13
 *
 * 反射的应用场景：https://blog.csdn.net/zolalad/article/details/29370565
 * 反射的一些常用操作：参考：https://blog.csdn.net/sinat_38259539/article/details/71799078
 *
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


    //**********字段*************//
    public String name;
    protected int age;
    char sex;
    private String phoneNum;




    //**************成员方法***************//
    public void show1(String s){
        System.out.println("调用了：公有的，String参数的show1(): s = " + s);
    }
    protected void show2(){
        System.out.println("调用了：受保护的，无参的show2()");
    }
    void show3(){
        System.out.println("调用了：默认的，无参的show3()");
    }
    private String show4(int age){
        System.out.println("调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }



    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex
                + ", phoneNum=" + phoneNum + "]";
    }


    public static void main(String[] args) {
        System.out.println("main方法执行了。。。");
    }

    public void show(){
        System.out.println("is show()");
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
     *
     * 2.设置字段的值：
     * 		Field --> public void set(Object obj,Object value):
     * 		参数说明：
     * 		1.obj:要设置的字段所在的对象；
     * 		2.value:要为字段设置的值；
     *
     *
     */



    //通过反射获取构造方法并使用
    @Test
    public void test01() {

        //1.加载Class对象
        Class clazz = null;
        try {
            clazz = Class.forName("thinkinginjava.chapter_14_classInfo.Student");
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




    //获取成员变量并调用
    @Test
    public void test02() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        //1.获取Class对象
        Class stuClass = Class.forName("thinkinginjava.chapter_14_classInfo.Student");
        //2.获取字段
        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = stuClass.getFields();
        for(Field f : fieldArray){
            System.out.println(f);
        }
        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
        fieldArray = stuClass.getDeclaredFields();
        for(Field f : fieldArray){
            System.out.println(f);
        }
        System.out.println("*************获取公有字段xx并调用***********************************");
        Field f = stuClass.getField("name");
        System.out.println(f);
        //获取一个对象
        Object obj = stuClass.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
        //为字段设置值
        f.set(obj, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"
        //验证
        Student stu = (Student)obj;
        System.out.println("验证姓名：" + stu.name);


        System.out.println("**************获取私有字段****并调用********************************");
        f = stuClass.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);//暴力反射，解除私有限定
        f.set(obj, "18888889999");
        System.out.println("验证电话：" + stu);

    }



    //获取成员方法并调用
    @Test
    public void test03() throws Exception {

        //1.获取Class对象
        Class stuClass = Class.forName("thinkinginjava.chapter_14_classInfo.Student");
        //2.获取所有公有方法
        System.out.println("***************获取所有的”公有“方法*******************");
        stuClass.getMethods();
        Method[] methodArray = stuClass.getMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        System.out.println("***************获取所有的方法，包括私有的*******************");
        methodArray = stuClass.getDeclaredMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }
        System.out.println("***************获取公有的show1()方法*******************");
        Method m = stuClass.getMethod("show1", String.class);
        System.out.println(m);
        //实例化一个Student对象
        Object obj = stuClass.getConstructor().newInstance();
        m.invoke(obj, "刘德华");

        System.out.println("***************获取私有的show4()方法******************");
        m = stuClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);//解除私有限定
        Object result = m.invoke(obj, 20);//需要两个参数，一个是要调用的对象（获取有反射），一个是实参
        System.out.println("返回值：" + result);




    }


    //反射main方法
    @Test
    public void test04(){
        try {
            //1、获取Student对象的字节码
            Class clazz = Class.forName("thinkinginjava.chapter_14_classInfo.Student");

            //2、获取main方法
            Method methodMain = clazz.getMethod("main", String[].class);//第一个参数：方法名称，第二个参数：方法形参的类型，
            //3、调用main方法
            // methodMain.invoke(null, new String[]{"a","b","c"});
            //第一个参数，对象类型，因为方法是static静态的，所以为null可以，第二个参数是String数组，这里要注意在jdk1.4时是数组，jdk1.5之后是可变参数
            //这里拆的时候将  new String[]{"a","b","c"} 拆成3个对象。。。所以需要将它强转。
            methodMain.invoke(null, (Object)new String[]{"a","b","c"});//方式一
            //methodMain.invoke(null, new Object[]{new String[]{"a","b","c"}});//方式二

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //反射方法的其它使用之---通过反射运行配置文件内容
    @Test
    public void test05() throws Exception {
        Properties pro = new Properties();
        {//此方式要求   配置文件在 src 文件夹 内

            //类名.class.getClassLoader().getResourceAsStream("文件名")
            InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("pro.properties");
            pro.load(inStream);
            inStream.close();
        }

        //通过反射获取Class对象
        Class stuClass = Class.forName(pro.getProperty("className"));//"cn.fanshe.Student"
        //2获取show()方法
        Method m = stuClass.getMethod(pro.getProperty("methodName"));//show
        //3.调用show()方法
        m.invoke(stuClass.getConstructor().newInstance());

    }



    //反射方法的其它使用之---通过反射越过泛型检查
    @Test
    public void test06() throws Exception{
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("aaa");
        strList.add("bbb");

        //	strList.add(100);
        //获取ArrayList的Class对象，反向的调用add()方法，添加数据
        Class listClass = strList.getClass(); //得到 strList 对象的字节码 对象
        //获取add()方法
        Method m = listClass.getMethod("add", Object.class);
        //调用add()方法
        m.invoke(strList, 100);

        //遍历集合
        for(Object obj : strList){
            System.out.println(obj);
        }

    }





}




