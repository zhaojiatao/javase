package thinkinginjava.chapter_21_thread.part_21_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 */
public class MutexEvenGenerator extends IntGenerator {

    private int currentEvenValue=0;
    private Lock lock=new ReentrantLock();

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
