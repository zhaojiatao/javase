package thinkinginjava.chapter_15_generics.Test_15_3;

/**
 * @author zhaojiatao
 * @date 2018/9/27
 */
public class Test implements Generator<Integer> {


    @Override
    public Integer next() {
        return 1;
    }

    public static void main(String[] args) {
        Test test=new Test();
        System.out.println(test.next());
    }
}

class Test2 implements Generator<String> {


    @Override
    public String next() {
        return "hello";
    }

    public static void main(String[] args) {
        Test2 test2=new Test2();
        System.out.println(test2.next());
    }
}


interface Generator<T>{
    T next();
}
