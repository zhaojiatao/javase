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
    public String call(){
        return "result of TaskWithResult"+id;
    }
}
