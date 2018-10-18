package thinkingInJava.chapter_15_generics.Test_15_2_1;

/**
 * @author zhaojiatao
 * @date 2018/10/6
 */
public class ThreeTuple<A,B,C> extends TwoTuple<A,B>{
    public final C third;
    public ThreeTuple(A first, B second, C third) {
        super(first, second);
        this.third = third;
    }
    @Override
    public String toString() {
        return "("+first+","+second+","+third+")";
    }

}
