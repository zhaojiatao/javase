package thinkinginjava.chapter_21;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/27
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
    private static ThreadLocal<Integer> value=new
            ThreadLocal<Integer>(){
                private Random rand=new Random(47);
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

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<1;i++){
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(1);
        exec.shutdownNow();
    }
}