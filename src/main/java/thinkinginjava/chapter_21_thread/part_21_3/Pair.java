package thinkinginjava.chapter_21_thread.part_21_3;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojiatao
 * @date 2019/1/22
 *
 * 这个类主要用来证明：用同步代码块的方式要比同步方法更加快。
 * 同步代码块：
 * 防止多个线程同时访问方法内部的部分代码而不是防止访问整个方法
 * 在进入同步代码块之前，必须拿到syncObject上的锁
 * 优点：同步代码块和同步方法相比可以提供多任务访问对象的时间性能
 *
 */
public class Pair {

    private int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX() {
        x++;
    }

    public void incrementY() {
        y++;
    }

    @Override
    public String toString() {
        return "x: " + x + ",y: " + y;
    }

    public class PairValuesNotEqualException extends RuntimeException {
        public PairValuesNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y) {
            throw new PairValuesNotEqualException();
        }
    }
}


abstract class PairManager{
    AtomicInteger checkCounter=new AtomicInteger(0);
    protected Pair p=new Pair();
    private List<Pair> storage= Collections.synchronizedList(new ArrayList<Pair>());

    public synchronized Pair getPair() {
        return new Pair(p.getX(),p.getY());
    }

    protected void store(Pair p){
        storage.add(p);
        try{
            TimeUnit.MILLISECONDS.sleep(50);
        }catch (InterruptedException ignore){

        }
    }

    public abstract void increment();

}

class PairManager1 extends PairManager{

    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}


class PairManager2 extends PairManager{

    @Override
    public void increment() {
        Pair temp;
        synchronized (this){
            p.incrementX();
            p.incrementY();
            temp=getPair();
        }
        store(temp);
    }
}

class PairManipulator implements Runnable{
    private PairManager pm;
    public PairManipulator(PairManager pm){
        this.pm=pm;
    }

    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair: "+pm.getPair()+" checkCounter ="+pm.checkCounter.get();
    }
}

class PairChecker implements Runnable{
    private PairManager pm;
    public PairChecker(PairManager pm){
        this.pm=pm;
    }

    @Override
    public void run() {
        while (true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}


class CriticalSetion{
     static void testApproaches(PairManager pman1,PairManager pman2){
        ExecutorService exec= Executors.newCachedThreadPool();
        PairManipulator
                pm1=new PairManipulator(pman1),
                pm2=new PairManipulator(pman2);
        PairChecker
                pcheck1=new PairChecker(pman1),
                pchech2=new PairChecker(pman2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pcheck1);
        exec.execute(pchech2);
        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()+"\npm1: "+pm1+"\npm2: "+pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager
                pman1=new PairManager1(),
                pman2=new PairManager2();
        testApproaches(pman1,pman2);
    }
}


class ExplicitPairManager1 extends PairManager{

    private Lock lock=new ReentrantLock();
    @Override
    public synchronized void increment() {
            lock.lock();
            try{
                p.incrementX();
                p.incrementY();
                store(getPair());
            }finally {
                lock.unlock();
            }

    }
}

class ExplicitPairManager2 extends PairManager{
    private Lock lock=new ReentrantLock();
    @Override
    public void increment() {
        Pair temp;
        lock.lock();
        try{
            p.incrementX();
            p.incrementY();
            temp=getPair();
        }finally {
            lock.unlock();
        }
        store(temp);

    }
}

class ExplicitCriticalSetion{
    public static void main(String[] args) {
        PairManager
                pman1=new ExplicitPairManager1(),
                pman2=new ExplicitPairManager2();
        CriticalSetion.testApproaches(pman1,pman2);
    }
}



