package thinkinginjava.chapter_21;

import thinkinginjava.chapter_21.part_21_2_1.Liftoff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec= Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++){
            exec.execute(new Liftoff());
        }
        exec.shutdown();
    }
}
