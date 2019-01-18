package thinkinginjava.chapter_14_classInfo.Test2;


/**
 * @author zhaojiatao
 * @date 2018/9/9
 *
 * 1、什么是Class对象，Class对象是用来做什么的？
 *   Class对象是java程序用来创建类的所有常规对象用的；每个类都有一个Class对象；
 * 2、Class对象是如何创建的？
 *   当程序创建第一个对类的静态成员(static修饰的成员以及构造方法)的引用时，就会加载这个类。类加载器首先检查这个类的
 *   Class对象是否已经加载；如果尚未加载，默认的类加载器就会查找.class文件。在加载字节码后会执行安全验证，之后会根
 *   据字节码在内存中创建这个类的Class对象；
 * 3、除了jvm的类加载器会获取某个类的Class对象之外，我们自己如何获取某个类的Class对象的引用？
 *    这个例子会介绍第一种方法：使用Class类的forName()方法，获取类的Class对象的引用；这个方法的副作用是，如果jvm
 *    还又有加载这个类的Class对象的话就加载这个类；在类加载的过程中，会初始化static成员；
 *    注意，传递给forName方法的名字，必须是全限定名；
 *    此外，还可以使用类字面常量的方法来生成对类Class对象的引用；
 *
 *
 */
interface HasBatteries{}

interface Waterproof{}

interface Shoots{}

class Toy{
    Toy(){}
    Toy(int i){}
}

class FancyToy extends Toy implements HasBatteries,Waterproof,Shoots{
    FancyToy(){
        super(1);
    }
}

public class Test2 {

    //通过Class对象，可以得到Class对象的多有信息
    static void printInfo(Class cc){
        System.out.println("Class name:"+cc.getName()+"is interface?["+cc.isInterface()+"]");
        System.out.println("Simple name:"+cc.getSimpleName());
        System.out.println("Canonical name:"+cc.getCanonicalName());
    }


    public static void main(String[] args) {
        Class c=null;

        try{
            //注意，必须使用全限定名
            c=Class.forName("thinkinginjava.chapter_14_classInfo.Test2.FancyToy");
        }catch (ClassNotFoundException e){
            System.out.println("Can't find Test2");
            System.exit(1);
        }

        printInfo(c);

        for(Class face:c.getInterfaces()){
            printInfo(face);
        }

        Class up=c.getSuperclass();
        Object obj=null;
        try{
            //使用newInstance来创建的类，必须带有默认构造器
            obj=up.newInstance();
        }catch(InstantiationException e){
            System.out.println("Cannot instantiate");
            System.exit(1);
        }catch (IllegalAccessException e){
            System.out.println("Cannot access");
            System.exit(1);
        }
        printInfo(obj.getClass());

        System.out.println(obj instanceof Toy);

    }


}










