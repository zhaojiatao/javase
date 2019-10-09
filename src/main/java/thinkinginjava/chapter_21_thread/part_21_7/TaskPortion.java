package thinkinginjava.chapter_21_thread.part_21_7;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-10-09
 * 使用CountDownLatch完成异步转同步
 */

/**
 * TaskPortion将随机地休眠一段时间，以模拟这部分工作的完成
 */
class TaskPortion implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static Random rand = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            //其他任务在结束其工作时，可以在CountDownLatch对象上调用countDown()来减小这个计数值。
        } catch (InterruptedException ex) {
            // Acceptable way to exit
            ex.printStackTrace();
        }finally {
            //这个最好在finally中调用
            latch.countDown();
        }
    }

    /**
     * 模拟工作，随机休眠一段时间
     * @throws InterruptedException
     */
    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        System.out.println(this + "completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

/**
 * Waits on the CountDownLatch:
 *
 * WaitingTask表示系统中必须等待的部分
 * 要等待待问题的初始部分完成为止。
 * 所有任务都使用了在main中定义的同一个单一的CountDownLatch
 */
class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            //任何在这个CountDownLatch对象调用await()方法都将阻塞，直至这个计数值到达0。
            latch.await();
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException ex) {
            System.out.println(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object:
        //向CountDownLatch对象设置一个初始计数值
        CountDownLatch latch = new CountDownLatch(SIZE);


        //创建十个任务，都阻塞在await方法上，直到下面的100个任务都执行完，并且通过coutdown将size值清空为零，则await上阻塞的任务才继续执行
        //这就是所谓的异步转同步
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(latch));
        }

        //100个任务都执行完，并且通过coutdown将size值清空为零，则await上阻塞的任务才继续执行
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(latch));
        }

        System.out.println("Launched all tasks");

        exec.shutdown(); // Quit when all tasks complete
    }
} /* (Execute to see output) *///:~
