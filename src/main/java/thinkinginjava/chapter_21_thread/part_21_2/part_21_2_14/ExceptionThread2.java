package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author zhaojiatao
 * @date 2019/1/19
 */
public class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        Thread t=Thread.currentThread();
        System.out.println("run() by "+t);
        System.out.println("eh = "+t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

/**
 * 自定义一个线程异常处理器
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught "+e);
    }
}

class HandlerThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this+" creating new Thread");
        Thread t=new Thread(r);
        System.out.println("created "+t);
        //在线程工厂生产线程的方法中为生产的新线程设置自定义的异常处理器
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh="+t.getUncaughtExceptionHandler());
        return t;
    }
}

class CaptureUncaughtException{
    public static void main(String[] args) {

        try{
            //创建ExectorService的时候指定自定义的线程工厂
            ExecutorService exec= Executors.newCachedThreadPool(
                    new HandlerThreadFactory()
            );
            exec.execute(new ExceptionThread2());
        }catch (Exception e){
            System.out.println("主线程捕获了异常："+e.getMessage());
        }
    }
}

class SettingDefaultHandler{
    public static void main(String[] args) {
        /*Thread.setDefaultUncaughtExceptionHandler(
                new MyUncaughtExceptionHandler()
        );*/
        try{
            ExecutorService exec=Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch (Exception e){
            System.out.println("主线程捕获了异常："+e.getMessage());
        }

    }
}












