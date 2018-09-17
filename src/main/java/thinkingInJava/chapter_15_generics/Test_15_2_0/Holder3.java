package thinkingInJava.chapter_15_generics.Test_15_2_0;

/**
 * @author zhaojiatao
 * @date 2018/9/16
 */
public class Holder3<T> {
    private T a;

    public Holder3(T a) {
        this.a=a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getA() {
        return a;
    }

    public static void main(String[] args) {
        Holder3<Automobile> h3=new Holder3<Automobile>(new Automobile());
        Automobile a=h3.getA();
    }

}


class Automobile{}