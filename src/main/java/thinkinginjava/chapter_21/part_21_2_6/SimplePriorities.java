package thinkinginjava.chapter_21.part_21_2_6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 *
 * 优先级低的程序仅仅是执行的频率较低，而不是不执行
 * 试图操作线程的优先级通常是一种错误
 *
 */
public class SimplePriorities implements Runnable{

    private int countDown=5;
    private volatile double d;
    private int priority;
    public SimplePriorities(int priority){
        this.priority=priority;
    }
    public String toString(){
        return Thread.currentThread()+":"+countDown;
    }

    public void run(){
        Thread.currentThread().setPriority(priority);

        while (true){
            for (int i=1;i<100000;i++){
                d +=(Math.PI+Math.E)/(double) i;
                if(i % 1000 ==0){
                    Thread.yield();
                }
            }
            System.out.println(this);
            if(--countDown==0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        }
        exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        exec.shutdown();
    }


}
