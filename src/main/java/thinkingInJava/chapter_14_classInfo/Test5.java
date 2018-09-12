package thinkingInJava.chapter_14_classInfo;

/**
 * @author zhaojiatao
 * @date 2018/9/12
 *
 *   14.5 instanceof 与 Class的等价性
这一章节，其实主要就是讲解instanceof、isInstance()、class对象== 三者的不同；

　instanceof和isInstance()是一组，结果是相同的，区别在前文已经说过，前者需要在编译器进行类型检查，后者只在运行时进行类型检查；

而比较class对象的==和equals一组，结果也是相同的，应为equals是比较两个class对象的内存地址是否一致；

　 但综合起来看，这两组的结果是不一样的。instance和isInstance()考虑了继承的情况，而后一组没有；
 */
public class Test5 {
    public static void main(String[] args) {
        FamilyVsExactType.test(new Base());
        FamilyVsExactType.test(new Derived());
    }
}


class Base{}
class Derived extends Base{}

class FamilyVsExactType {
    static void test(Object x){
        System.out.println("Testing x of type "+x.getClass());
        System.out.println("x instanceof Base "+(x instanceof Base));
        System.out.println("x instanceof Derived "+Derived.class.isInstance(x));
        System.out.println("Base.isInstance(x) "+Base.class.isInstance(x));
        System.out.println("Derived.isInstance(x) "+Derived.class.isInstance(x));
        System.out.println("x.getClass()==Base.class "+(x.getClass()==Base.class));
        System.out.println("x.getClass()==Derived.class "+(x.getClass()==Derived.class));
        System.out.println("x.getClass().equals(Base.class) "+x.getClass().equals(Base.class));
        System.out.println("x.getClass().equals(Derived.class) "+x.getClass().equals(Derived.class));
    }
}