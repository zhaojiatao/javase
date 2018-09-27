package thinkingInJava.chapter_15_generics.Test_15_4_0;

/**
 * @author zhaojiatao
 * @date 2018/9/27
 */
public class GenericMethods {
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethods genericMethods=new GenericMethods();
        genericMethods.f(" ");
        genericMethods.f(1);
        genericMethods.f(1.0);
        genericMethods.f(1.0F);
        genericMethods.f('c');
        genericMethods.f(genericMethods);
    }
}
