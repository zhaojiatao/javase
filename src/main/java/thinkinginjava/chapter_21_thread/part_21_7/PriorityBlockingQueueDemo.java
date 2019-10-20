package thinkinginjava.chapter_21_thread.part_21_7;
//: concurrency/PriorityBlockingQueueDemo.java

import java.util.concurrent.*;
import java.util.*;

/**
 * 优先级队列
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random rand = new Random(47);
    /**
     * counter类静态变量 初始值为零
     */
    private static int counter = 0;
    /**
     * 每个任务的id都是从counter类静态变量的旧值基础上自增加一
     */
    private final int id = counter++;
    /**
     * 每个任务都有自己的优先级
     */
    private final int priority;
    /**
     * sequence确保了任务被创建的顺序
     */
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PrioritizedTask arg) {
        return priority < arg.priority ? 1 : (priority > arg.priority ? -1 : 0);
    }

    /**
     * 队列中线程执行的方法，随机休眠0～250毫秒并打印当前线程
     */
    @Override
    public void run() {
        try {
            //线程每次进入休眠时，如果被执行了shutdown则会被中断，并抛出异常
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        System.out.println(this);
    }

    /**
     * 重写toString方法，并打印出当前任务的优先级及任务id
     * @return
     */
    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    /**
     * 静态内部类
     * 构造方法中引用父类的构造方法，并设置优先级为-1
     */
    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService exec;

        public EndSentinel(ExecutorService e) {
            // Lowest priority in this program
            super(-1);
            exec = e;
        }

        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTask pt : sequence) {
                //遍历任务数组并打印任务的摘要信息
                System.out.print(pt.summary());
                //每五个任务打一个换行
                if (++count % 5 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            //对exec的每个线程执行依次interapt方法
            exec.shutdownNow();
        }
    }
}

/**
 * 优先级任务生产者
 */
class PrioritizedTaskProducer implements Runnable {
    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;

    /**
     * 通过构造方法将优先级队列初始化进来
     * 并将线程容器初始化进来，在停止时使用
     * @param q
     * @param e
     */
    public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e) {
        queue = q;
        // Used for EndSentinel
        exec = e;
    }

    /**
     * 生产者的任务详细过程
     */
    @Override
    public void run() {
        // Unbounded queue; never blocks.
        // Fill it up fast with random priorities:
        //向优先级队列中快速填充了20个任务，可以想象一下并发10000个订单的情况
        for (int i = 0; i < 20; i++) {
            queue.add(new PrioritizedTask(rand.nextInt(10)));
            //让cpu让渡一下使用权，也有可能继续
            Thread.yield();
        }
        // Trickle in highest-priority jobs:
        try {
            //每隔250毫秒插入最高优先级的任务
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            //从最低到最高的任务依次插入
            // Add jobs, lowest priority first:
            for (int i = 0; i < 10; i++) {
                queue.add(new PrioritizedTask(i));
            }
            //优先级队列中添加结束任务
            // A sentinel to stop all the tasks:
            queue.add(new PrioritizedTask.EndSentinel(exec));
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}

/**
 * 优先级任务消费者
 */
class PrioritizedTaskConsumer implements Runnable {
    /**
     * 优先级队列
     */
    private PriorityBlockingQueue<Runnable> q;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            //只要线程没有被中断，就从优先级队列中取值并执行。如果没有可用的任务，则阻塞
            while (!Thread.interrupted()) {
                // Use current thread to run the task:
                q.take().run();
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) throws Exception {
        Random rand = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        //定义了一个空的优先级队列
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        //启动了一个线程作为任务的生产者
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        //启动了一个线程作为任务的消费者
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
} /* (Execute to see output) *///:~
