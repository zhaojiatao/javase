package thinkinginjava.chapter_21_thread.part_21_3;


/**
 * @author zhaojiatao
 * @date 2019/1/20
 *
 */
public class SynchronizedEvenGenerator extends IntGenerator {
    private int currentEvenValue=0;

    /**
     * 通过synchronized来同步
     * 不会产生任何失败
     * 任何时刻只有一个任务可以通过由互斥量看护的代码
     * @return
     */
    @Override
    public synchronized int next() {
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}
