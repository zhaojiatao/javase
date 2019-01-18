package thinkinginjava.chapter_21;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        ArrayList<Future<String>> results=new ArrayList<>();
        for(int i=0;i<10;i++){
            Future<String> future=exec.submit(new TaskWithResult(i));
            System.out.println(future.isDone());
            results.add(future);
        }

        for(Future<String> fs:results){
            try{
                System.out.println(fs.get());
            }catch (Exception e){

            }finally {
                exec.shutdown();
            }
        }

    }
}
