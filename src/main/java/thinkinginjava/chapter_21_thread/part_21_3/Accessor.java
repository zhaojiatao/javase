package thinkinginjava.chapter_21_thread.part_21_3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/27
 * 防止任务在共享资源上产生冲突的第二种方式是根除对变量的共享
 */
public class Accessor implements Runnable{
    private final int id;
    public Accessor(int idn){
        id=idn;
    }
    @Override
    public void run() {
        for(int i=0;i<5;i++){
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#"+id+":"+ThreadLocalVariableHolder.get();
    }
}


class ThreadLocalVariableHolder{
    /**
     * ThreadLocal对象通常当作静态域存储
     * 只能通过get()和set()方法访问该对象的内容
     * get()方法将返回存储中原有的对象
     */
    private static ThreadLocal<Integer> value=new
            ThreadLocal<Integer>(){
                private Random rand=new Random(47);
                @Override
                protected synchronized Integer initialValue(){
                    int i=rand.nextInt(10000);
                    //System.out.println(Thread.currentThread().toString()+i);
                    return i;
                }
            };
    public static void increment(){
        int getInt=value.get();
        value.set(getInt+1);
    }
    public static int get(){
        return value.get();
    }

    /**
     * 开启五个线程
     * 每个线程都被分配了自己的存储，每个线程都需要跟踪自己的计数值。
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(1);
        exec.shutdownNow();
    }
}