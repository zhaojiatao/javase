package thinkinginjava.chapter_15_generics.Test_15_2_1;

/**
 * @author zhaojiatao
 * @date 2018/10/6
 */
public class Test {

    static TwoTuple<String ,Integer> f(){
        return new TwoTuple<String ,Integer>("hi",47);
    }

    static ThreeTuple<Amphibian,String,Integer> g(){
        return new ThreeTuple<Amphibian,String,Integer>(new Amphibian(),"hi",47);
    }

    public static void main(String[] args) {
        TwoTuple<String ,Integer> ttsi=f();
        System.out.println(ttsi);
        System.out.println(g());
    }

}

class Amphibian{}

class Vehicle{}