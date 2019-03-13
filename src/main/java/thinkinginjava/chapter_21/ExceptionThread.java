package thinkinginjava.chapter_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojiatao
 * @date 2019/1/19
 */
public class ExceptionThread implements Runnable{
    @Override
    public void run() {
            throw new RuntimeException();
    }

    public static void main(String[] args) {
        try{
            ExecutorService exec= Executors.newCachedThreadPool();
            //exec.execute(new ExceptionThread());
            exec.submit(Executors.callable(new ExceptionThread())).get();
        }catch (Exception e){
            System.out.println("主线程发现异常:"+e.toString());
        }
    }
}
