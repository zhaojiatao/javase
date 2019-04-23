package thinkinginjava.chapter_21;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */


public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        try{

            ArrayList<Future<String>> results=new ArrayList<>();
            for(int i=0;i<15;i++){
                Future<String> future=exec.submit(new TaskWithResult(i));
                System.out.println(future.isDone());
                results.add(future);
            }

            for(Future<String> fs:results){
                try {
                    System.out.println(fs.get(15, TimeUnit.SECONDS));
                }catch(Exception e){
                    System.out.println(fs.toString()+e.toString());
                }

            }
        }catch (Exception e){
            System.out.println("主线程执行发现异常："+e.getMessage());
        }finally {
            exec.shutdown();
        }
    }



}
