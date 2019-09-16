package thinkinginjava.chapter_21.interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本事例证明：
 * sleep状态下的阻塞可以中断；而i/o阻塞和synchronize锁等待阻塞无法中断。
 *
 * @author zhaojiatao
 * @date 2019/3/25
 */
public class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            //此处睡眠100秒，此处阻塞会被中断
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream in;

    public IOBlocked(InputStream is) {
        in = is;
    }

    @Override
    public void run() {
        System.out.println("Waiting for read");
        try {
            //坚挺控制台输入，阻塞
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");

    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        while (true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        //在new线程对象的时候就会执行下面的方法，启动一个线程并执行f()方法，占用本SynchronizedBlocked对象的锁
        new Thread() {
            @Override
            public void run() {
                f();
            }
        }.start();

    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();//将无法获取锁，阻塞
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}

class Interrupting {
    private static ExecutorService exec =
            Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }

}

/**
 * thread.interrupt()方法可以中断由于ReentrantLock()方法lock而导致的互斥阻塞
 */

class BlockedMutex {
    private Lock lock = new ReentrantLock();

    //通过构造方法获取到资深的lock，并且从不释放这个锁
    public BlockedMutex() {
        lock.lock();
    }

    public void f() {
        try {
            //用此种方式上锁，如果发生阻塞可以被中段。由于当前对象被锁住，故此处需要待lock释放当前对象才能上锁，故发生锁等待阻塞。
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Blocked2 implements Runnable {

    //使用构造方法初始化对象时就上锁了
    BlockedMutex blocked = new BlockedMutex();

    @Override
    public void run() {
        System.out.println("waiting for f() in BlockedMutex");
        blocked.f();
        System.out.println("broken out of blocked call");
    }
}

class Interrupting2 {
    public static void main(String[] args) {
        //new Blocked2的时候，就会把Blocked2对象锁住
        Thread t = new Thread(new Blocked2());
        //t.start()方法在执行blocked.f();的时候会因为blocked对象的锁被占用，而产生锁等待阻塞。
        t.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //最后这个中断的方法会中断锁阻塞
        t.interrupt();
    }

}



























