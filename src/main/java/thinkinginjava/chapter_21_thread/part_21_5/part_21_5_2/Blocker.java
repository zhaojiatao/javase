package thinkinginjava.chapter_21_thread.part_21_5.part_21_5_2;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-09-18
 * notifyAll必须在同步方法中被调用。当notifyAll因某个特定锁而被调用的时候，只有等待这个锁的任务才会被唤醒
 */
public class Blocker {

    synchronized void waitingCall() {
        try {
            while (!Thread.interrupted()) {
                //当main函数中执行shutdownnow的时候，当在执行wait方法的瞬间，会因为要进入则色状态而抛中断异常
                wait();
                System.out.println(Thread.currentThread() + "");
            }
        } catch (InterruptedException e) {
            //ok to exit this way
        }
    }

    /**
     * synchronized 关键字 意味着它们将获取自身的锁，因此，在调用notify或notifyAll的时候，只在这个锁上调用是符合逻辑的。
     * 因此将只唤醒在等待这个特定锁的任务。
     */
    synchronized void prod(){
        notify();
    }

    synchronized void prodAll(){
        notifyAll();
    }

}

class Task implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

/**
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-5,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-2,5,main]
 *
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-2,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-5,5,main]
 *
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-5,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-2,5,main]
 *
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-2,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-5,5,main]
 *
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-5,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-2,5,main]
 *
 * notify()
 * Thread[pool-1-thread-1,5,main]
 *
 * notifyAll()
 * Thread[pool-1-thread-1,5,main]
 * Thread[pool-1-thread-2,5,main]
 * Thread[pool-1-thread-3,5,main]
 * Thread[pool-1-thread-4,5,main]
 * Thread[pool-1-thread-5,5,main]
 *
 * Timer canceled
 * Task2.blocker.proAll()
 * Thread[pool-1-thread-6,5,main]
 *
 * Shutting down
 */
class NotifyVsNotifyAll{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            //5个Task类型的线程，先后抢占静态对象Task.blocker的锁，并进入等待。只有这个静态对象blocker的锁对应的notifyall时这五个挂起的线程才会继续
            exec.execute(new Task());
        }
        //Task2类型的线程抢占静态对象Task2.blocker的锁，并进入等待
        exec.execute(new Task2());
        Timer timer=new Timer();

        //开启一个任务，每隔4/10秒执行一次run()方法
        //可以看到，即使村咋爱Task2.blocker上阻塞的Task2对下昂，也没有任何在Task.blocker上的notify或notifyAll调用会导致Task2对象被唤醒。
        timer.scheduleAtFixedRate(new TimerTask() {
            //是否执行notify方法，第一次默认是
            boolean prod=true;
            @Override
            public void run() {
                if(prod){
                    System.out.println("\nnotify()");
                    Task.blocker.prod();
                    prod=false;
                }else {
                    System.out.println("\nnotifyAll()");
                    Task.blocker.prodAll();
                    prod=true;
                }
            }
        },400,400);

        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("\nTimer canceled");
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Task2.blocker.proAll()");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("\nShutting down");
        exec.shutdownNow();

    }
}
















