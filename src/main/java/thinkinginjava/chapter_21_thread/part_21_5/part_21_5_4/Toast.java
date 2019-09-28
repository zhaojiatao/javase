package thinkinginjava.chapter_21_thread.part_21_5.part_21_5_4;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-09-28
 * 一台机器具有三个任务：一个制作吐司。一个给吐司抹黄油。另一个在抹过黄油的吐司上涂果酱。
 *
 * 每片吐司在任何时刻都只由一个任务在操作。
 * 因为队列的阻塞，使得处理过程将被自动地挂起和恢复。
 * 由BlockingQueue产生的简化十分明显。在使用显示地wait和notifyall时，存在的类与类之间的耦合被消除了，因为每个类都只和它的blockingQueue通信。
 */
public class Toast {

    public enum Status {
        DRY, BUTTERED, JAMMED
    }

    /**
     * 每个吐司在刚刚制作的时候new 出来，状态的初始值是dry，代表没有涂抹过任何佐料。
     */
    private Status status = Status.DRY;

    private final int id;

    public Toast(int idn) {
        id = idn;
    }

    public void butter() {
        status = Status.BUTTERED;
    }

    public void jam() {
        status = Status.JAMMED;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingDeque<Toast> {

}

/**
 * 制作吐司
 */
class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);

    /**
     * 初始化 制作吐司的任务一开始会初始化一个空的装在吐司的队列toastQueue
     * @param tq
     */
    public Toaster(ToastQueue tq) {
        toastQueue = tq;
    }

    @Override
    public void run() {
        try {
            //开始执行制作吐司的任务，只要当前线程没有中断就一只做下去。
            while (!Thread.interrupted()) {
                //随机休眠若干毫秒，让渡cpu使用权，主要是可以在线程shutdown的时候可以抛出异常中断run方法
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                //吐司对象Toast 从id=0开始
                Toast t = new Toast(count++);
                System.out.println(t);
                //insert into queue
                //把这个吐司放进用来装在吐司的队列中
                toastQueue.put(t);
            }
        } catch (InterruptedException e) {
            //线程中断时在进入阻塞前或正在进入阻塞时会抛异常，跳出run方法
            System.out.println("Toaster interrupted");
        }
        System.out.println("Toaster off");
    }
}

/**
 *
 */
class Butterer implements Runnable {
    private ToastQueue dryQueue;
    private ToastQueue butteredQueue;

    public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
        this.dryQueue = dryQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //Block until next piece of toast is available;
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(t);
                butteredQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}

class Jammer implements Runnable {

    private ToastQueue butteredQueue;
    private ToastQueue finishedQueue;

    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //Block until next piece of toast is available;
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.put(t);
            }
        } catch (InterruptedException e) {
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}

/**
 * 吃吐司
 */
class Eater implements Runnable {

    private ToastQueue finishedQueue;
    private int counter = 0;

    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //Block until next piece of toast is available;
                Toast t = finishedQueue.take();

                if (t.getId() != counter++ ||
                        t.getStatus() != Toast.Status.JAMMED) {
                    System.out.println(">>>> Error: " + t);
                    System.exit(1);
                } else {
                    System.out.println("Chomp! " + t);
                }

            }
        } catch (InterruptedException e) {
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}

class ToastMatic {
    public static void main(String[] args) throws InterruptedException {
        //创建三个队列，每一个队列都是一个LinkedBlockingDeque类型的阻塞队列。这种队列不限制长度。其内部的元素都是Toast吐司对象。
        ToastQueue dryQueue = new ToastQueue();
        ToastQueue butteredQueue = new ToastQueue();
        ToastQueue finishedQueue = new ToastQueue();

        ExecutorService exec = Executors.newCachedThreadPool();
        //开启任务 制作吐司
        exec.execute(new Toaster(dryQueue));
        //开启任务 给做好的吐司涂抹黄油
        exec.execute(new Butterer(dryQueue, butteredQueue));
        //开启任务 给涂抹好黄油的吐司涂抹果酱
        exec.execute(new Jammer(butteredQueue, finishedQueue));
        //开启任务 把涂抹好果酱的吐司吃掉
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();

    }
}



















