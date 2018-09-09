package thinkingInJava.chapter_14_classInfo;


/**
 * @author zhaojiatao
 * @date 2018/9/9
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
            c=Class.forName("thinkingInJava.chapter_14_classInfo.FancyToy");
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










