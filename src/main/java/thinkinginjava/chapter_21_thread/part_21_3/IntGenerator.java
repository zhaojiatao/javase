package thinkinginjava.chapter_21_thread.part_21_3;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 */
public abstract class IntGenerator {
    private volatile boolean canceled=false;
    public abstract int next();

    public void cancel(){
        canceled=true;
    }
    public boolean isCanceled() {
        return canceled;
    }
}
