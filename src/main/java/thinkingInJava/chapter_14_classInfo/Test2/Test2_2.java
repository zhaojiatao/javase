package thinkingInJava.chapter_14_classInfo.Test2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/9/11
 *
 *
 * 学习范型在Class引用的使用过程的应用
 *
 *
 */
public class Test2_2 {


    @Test
    public void Test01() {
        Class intClass=int.class;
        Class<Integer> genericIntClass=int.class;
        genericIntClass=Integer.class;
        //如果将genericIntClass这个Class引用赋值给double.class的话，会由于类型检查失败，是无法编译；
        //genericIntClass=double.class;
        //普通类的引用可以赋值为任何其他的Class对象；
        intClass = double.class;
    }


    //可以使用通配符?代替上例中的<Integer>，
    @Test
    public void Test02() {
        Class<?> intClass=int.class;
        intClass = double.class;
    }

    //如果我像创建一个Class对象的引用，并指定这个Class对象的类型为指定类型或其子类型，则需要使用? extend XXX
    @Test
    public void Test03() {
        Class<? extends Number> bounded=int.class;
        bounded = double.class;
        bounded = Number.class;
    }

    //至此，可以得出结论，使用范型语法的目的是为了提供编译期检查；

    @Test
    public void Test04(){
        FilledList<CountedInteger> fl=new FilledList<CountedInteger>(CountedInteger.class);
        System.out.println(fl.create(15));
    }



    @Test
    public void Test05() throws IllegalAccessException, InstantiationException {
        Class<A> a=A.class;
        A aa=a.newInstance();
        //注意，这里如果不写成这样会报错
        Class<? super A> b=a.getSuperclass();

        //注意：b.newInstance返回的不是精确值，而是Object;
        Object bb=b.newInstance();

    }



}

class CountedInteger{
    private static long counter;
    private final long id=counter++;
    public String toString(){
        return  Long.toString(id);
    }
}


//注意，由于这个类中使用了type.newInstance()方法，所以，必须保证T传进来的类有默认构造方法；
class FilledList<T>{
    private Class<T> type;
    public FilledList(Class<T> type){
        this.type=type;
    }

    public List<T> create(int nElements){
        List<T> result=new ArrayList<T>();
        try{
            for(int i=0;i<nElements;i++){
                //注意，当在type上使用范型，newInstance()方法将产生确定的类型；
                result.add(type.newInstance());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}



class A{

}

class B extends A{

}