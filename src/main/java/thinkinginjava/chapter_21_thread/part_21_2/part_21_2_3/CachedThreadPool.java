package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_3;

import thinkinginjava.chapter_21_thread.part_21_2.part_21_2_1.Liftoff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 *
 * ExecutorService将为你管理Thread对象。
 *
 */

/**
 * CachedThreadPool在程序执行过程中通常会创建与所需数量相同的线程。然后在它回收旧线程时停止创建新线程，因此是Executor的首选。
 * 只有当这种情况会引发问题时，才需要切换到FixedThreadPool
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            exec.execute(new Liftoff());
        }
        //shutdown执行前的任务将会继续执行，直到全部执行完毕。
        exec.shutdown();
    }
}

/**
 * 一次性预先执行代价高昂的线程分配，可以限制线程的数量
 */
class FixedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newFixedThreadPool(2);
        for(int i=0;i<5;i++){
            exec.execute(new Liftoff());
        }
        exec.shutdown();
    }
}

/**
 * SingleThreadExecutor 就像是线程数量为1的FixedThreadPool
 * 如果向SingleThreadExecutor提交了多个任务，那么这些将排队。
 */
class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++){
            exec.execute(new Liftoff());
        }
        exec.shutdown();
    }
}



















