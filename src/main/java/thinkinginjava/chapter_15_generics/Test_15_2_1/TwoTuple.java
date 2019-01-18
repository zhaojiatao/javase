package thinkinginjava.chapter_15_generics.Test_15_2_1;

/**
 * @author zhaojiatao
 * @date 2018/10/6
 */
public class TwoTuple<A,B>{
    public final A first;
    public final B second;
    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public String toString() {
        return "("+first+","+second+")";
    }
}
