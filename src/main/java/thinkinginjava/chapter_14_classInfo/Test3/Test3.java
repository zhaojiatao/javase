package thinkinginjava.chapter_14_classInfo.Test3;

import java.util.HashMap;
import java.util.Map;

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
        /*Father father = new Father();
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
        System.out.println(DynamicEqual(father, son));*/

        Son son=new Son();
        MyThreadTest myTest01=new MyThreadTest("thread01",son);
        Thread t1 = new Thread(myTest01);
        t1.start();

        MyThreadTest myTest02=new MyThreadTest("thread02",son);
        Thread t2 = new Thread(myTest02);
        t2.start();

    }


}

class Father{

    private ThreadLocal<String> detailDTOThreadLocal = new ThreadLocal<>();
    private Map<String,String> map=new HashMap<>();
    public String getString(String sonName){
        System.out.println(this.hashCode());
        if(sonName.equalsIgnoreCase("thread01")){
            detailDTOThreadLocal.set("thread01");
            map.put("threadName","thread01");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "thread01:"+this.detailDTOThreadLocal.get()+"\n"+"threadName:"+map.get("threadName");
        }
        if(sonName.equalsIgnoreCase("thread02")){
            detailDTOThreadLocal.set("thread02");
            map.put("threadName","thread02");
            return "thread02:"+this.detailDTOThreadLocal.get()+"\n"+"threadName:"+map.get("threadName");
        }

        return "other";
    }

}

class Son extends Father{

    public String getSonStringFromFather(String sonName){
        return super.getString(sonName);
    }

}

class MyThreadTest implements Runnable{
    String name;
    Son son;
    public MyThreadTest(String name,Son son) {
        this.name=name;
        this.son=son;
    }

    @Override
    public void run() {
        System.out.println(son.getSonStringFromFather(name));
        System.out.println(son.hashCode());
    }


}