package thinkinginjava.chapter_21_thread.part_21_7;

//: concurrency/DelayQueueDemo.java

import java.util.concurrent.*;
import java.util.*;

import static java.util.concurrent.TimeUnit.*;

/**
 * DelayQueue
 * java延迟队列提供了在指定时间才能获取队列元素的功能，队列头元素是最接近过期的元素，即延时低的任务在队头。
 * 没有过期元素的话，使用poll()方法会返回null值，超时判定是通过getDelay(TimeUnit.NANOSECONDS)方法的返回值小于等于0来判断。
 * 延时队列不能存放空元素。
 * 延时队列实现了Iterator接口，但iterator()遍历顺序不保证是元素的实际存放顺序。
 *
 * 由Delayed定义可以得知，队列元素需要实现getDelay(TimeUnit unit)方法和compareTo(Delayed o)方法,
 * getDelay定义了剩余到期时间，compareTo方法定义了元素排序规则.
 * 注意，元素的排序规则影响了元素的获取顺序，将在后面说明。
 *
 * 内部存储结构　
 * DelayedQuene的元素存储交由优先级队列存放,DelayedQuene的优先级队列使用的排序方式是队列元素的compareTo方法，
 * 优先级队列存放顺序是从小到大的，所以队列元素的compareTo方法影响了队列的出队顺序。
 * 若compareTo方法定义不当，会造成延时高的元素在队头，延时低的元素无法出队。
 *
 * https://www.cnblogs.com/hhan/p/10678466.html
 *
 */
class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;

    /**
     * sequence确保了任务被创建的顺序
     */
    protected static List<DelayedTask> sequence = new ArrayList<>();

    /**
     * 通过构造方法确定了这个延时任务要延时多久
     * @param delayInMilliseconds
     */
    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        //任务初始化时的系统时间
        trigger = System.nanoTime() +NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }

    /**
     * 用来告知延迟时间有多长
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    /**
     * 按照延迟时间排序
     * @param arg
     * @return
     */
    @Override
    public int compareTo(Delayed arg) {
        DelayedTask that = (DelayedTask) arg;
        if (trigger < that.trigger) { return -1;}
        if (trigger > that.trigger) { return 1;}
        return 0;
    }

    @Override
    public void run() { System.out.println(this + " "); }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) +
                " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }

        @Override
        public void run() {
            for (DelayedTask pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

/**
 * 自身就是一个任务，它有自己的线程，可以使用这个线程来运行从队列中获取到的所有任务
 */
class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //移除并返回队列头部的元素
                q.take().run(); // Run task with the current thread
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}

public class DelayQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        //定义一个延迟队列，里面放置了20个延时任务，每个任务的延时时间是随机的
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        // Fill with tasks that have random delays:
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(rand.nextInt(5000)));
        }

        // Set the stopping point
        //最后在队列中
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        //从任务队列中取任务并运行任务
        exec.execute(new DelayedTaskConsumer(queue));
    }
} /* Output:
[128 ] Task 11 [200 ] Task 7 [429 ] Task 5 [520 ] Task 18 [555 ] Task 1 [961 ] Task 4 [998 ] Task 16 [1207] Task 9 [1693] Task 2 [1809] Task 14 [1861] Task 3 [2278] Task 15 [3288] Task 10 [3551] Task 12 [4258] Task 0 [4258] Task 19 [4522] Task 8 [4589] Task 13 [4861] Task 17 [4868] Task 6 (0:4258) (1:555) (2:1693) (3:1861) (4:961) (5:429) (6:4868) (7:200) (8:4522) (9:1207) (10:3288) (11:128) (12:3551) (13:4589) (14:1809) (15:2278) (16:998) (17:4861) (18:520) (19:4258) (20:5000)
[5000] Task 20 Calling shutdownNow()
Finished DelayedTaskConsumer
*///:~
