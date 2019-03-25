package thinkinginjava.chapter_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            exec.execute(new Liftoff());
        }
        exec.shutdown();
    }
}
