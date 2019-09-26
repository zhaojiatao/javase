package thinkinginjava.chapter_21.part_21_2_4;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 *
 * 从任务中产生返回值
 * Callable<T> 中的泛型代表返回值类型
 * 必须使用submit方法调用它
 *
 */
public class TaskWithResult implements Callable<String>{
    private int id;
    public TaskWithResult(int id){
        this.id=id;
    }
    public String call() throws InterruptedException {
        if(id==4){
            System.out.println(Thread.currentThread().toString()+"开始睡眠");
            Thread.sleep(22000);
            System.out.println(Thread.currentThread().toString()+"醒来");
            //System.out.println(Thread.currentThread().toString()+"异常");
            //throw new RuntimeException(Thread.currentThread().toString()+"异常");
        }else if(id==8){
            System.out.println(Thread.currentThread().toString()+"开始睡眠");
            Thread.sleep(30000);
            System.out.println(Thread.currentThread().toString()+"醒来");
            //System.out.println(Thread.currentThread().toString()+"异常");
            //throw new RuntimeException(Thread.currentThread().toString()+"异常");
        }else{
            Thread.sleep(1000*id);
        }


        return "result of TaskWithResult"+id+"Thread.tostring:"+Thread.currentThread().toString();
    }
}

/**
 * get()方法可以获取结果，如果还没有返回结果，get方法将阻塞
 */
class CallableDemo {
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

