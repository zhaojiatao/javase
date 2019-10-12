package thinkinginjava.chapter_21_thread.part_21_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 * 用显示的Lock重写SynchronizedEvenGenerator
 */
public class MutexEvenGenerator extends IntGenerator {

    private int currentEvenValue=0;
    /**
     * 被互斥调用的锁
     */
    private Lock lock=new ReentrantLock();

    /**
     * 注意：return语句必须在try子句中出现，以确保unlock不会过早发生，以防止将数据暴露给了第二个任务
     * @return
     */
    @Override
    public int next() {
        lock.lock();
        try{
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;
            return currentEvenValue;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
}
