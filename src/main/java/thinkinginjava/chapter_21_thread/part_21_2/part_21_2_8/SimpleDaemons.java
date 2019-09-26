package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 * 后台线程
 *
 * 当所有的非后台线程结束时，程序也就终止了，同时会杀死进程中的所有后台线程。
 * 反过来说，如果有任何非后台线程还在执行，程序就不会终止。
 *
 */
public class SimpleDaemons implements Runnable{
    public void run(){
        try{
            while (true){
                TimeUnit.MILLISECONDS.sleep(700);
                System.out.println(Thread.currentThread()+""+this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<10;i++){
            Thread daemon=new Thread(new SimpleDaemons());
            //必须在线程启动之前设置为后台线程。
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(2300);
    }


}

class DaemonFromFactory implements Runnable{

    @Override
    public void run() {
        try{
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println(Thread.currentThread()+""+this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool(new DaemonThreadFactory());

        for(int i=0;i<10;i++){
            exec.execute(new DaemonFromFactory());
        }

        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(98);

    }
}

class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
    public DaemonThreadPoolExecutor(){

        super(0,Integer.MAX_VALUE,60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new DaemonThreadFactory());
    }

}

class Daemon implements Runnable{

    private Thread[] t=new Thread[10];
    public void run(){
        for(int i=0;i<10;i++){
            t[i]=new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("DaemonSpawn "+i+" started, ");
        }
        for(int i=0;i<t.length;i++){
            System.out.println("t["+i+"].isDaemon()="+
                    t[i].isDaemon()+",");
        }
        while (true){
            Thread.yield();
        }
    }


}


class DaemonSpawn implements Runnable{
    public void run(){
        while (true){
            Thread.yield();
        }
    }
}


class ADaemon implements Runnable{
    public void run(){

        try{
            System.out.println("starting ADaemon");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("this should always run?");
        }

    }

}
class DaemonDontRunFinally {
    public static void main(String[] args) {
        Thread t=new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
    }
}


/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t=new Thread(r);
        t.setDaemon(true);
        return t;
    }
}


class Daemons {
    public static void main(String[] args) throws InterruptedException {
        Thread d=new Thread(new Daemon());
        d.setDaemon(true);
        d.start();
        System.out.println("d.isDaemon()="+d.isDaemon()+",");
        TimeUnit.SECONDS.sleep(1);
    }
}

