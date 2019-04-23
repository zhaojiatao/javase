package thinkinginjava.chapter_21;

import java.util.concurrent.Callable;

/**
 * @author zhaojiatao
 * @date 2019/1/12
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
