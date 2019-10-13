package thinkinginjava.chapter_21_thread.part_21_3;

/**
 * @author zhaojiatao
 * @date 2019/1/24
 * synchronized块必须给定一个在其上进行同步的对象，并且最合理的方式是，使用其方法正在被调用的当前对象：synchronized(this)
 * 如果有时必须在另一个对象上同步，那么要确保所有相关的任务都是在同一个对象上同步的。
 */
public class DualSynch {

    private Object syncObject=new Object();
    public synchronized void f(){
        for(int i=0;i<5;i++){
            System.out.println("f()");
            Thread.yield();
        }
    }
    public void g(){
        synchronized (syncObject){
            for(int i=0;i<5;i++){
                System.out.println("g()");
                Thread.yield();
            }
        }
    }

}

/**
 * 两个任务f()和g()可以同时进入同一个对象。
 * f()方法是在ds对象上同步；
 * g()方法咋爱syncObject对象上同步，所以相互不影响。
 */
class SyncObject{
    public static void main(String[] args) {
        final DualSynch ds=new DualSynch();
        new Thread(){
            public void run(){
                ds.f();
            }
        }.start();
        ds.g();
    }
}