package thinkingInJava.chapter_21;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class DaemonFromFactory implements Runnable{

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
        ExecutorService exec=Executors.newCachedThreadPool(new DaemonThreadFactory());

        for(int i=0;i<10;i++){
            exec.execute(new DaemonFromFactory());
        }

        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(98);

    }
}
