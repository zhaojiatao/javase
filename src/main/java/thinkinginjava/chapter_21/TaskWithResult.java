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
        if(id==8){
            Thread.sleep(13000);
            System.out.println(Thread.currentThread().toString()+"异常");
            throw new RuntimeException(Thread.currentThread().toString()+"异常");
        }else{
            Thread.sleep(1000*id);
        }


        return "result of TaskWithResult"+id+"Thread.tostring:"+Thread.currentThread().toString();
    }
}
