package thinkinginjava.chapter_21;

import thinkinginjava.chapter_21.part_21_2_1.Liftoff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class SleepingTask extends Liftoff {

    @Override
    public void run() {
        try {

            while(countDown-->0){
                System.out.println(status());
                TimeUnit.MILLISECONDS.sleep(100);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++){
            exec.execute(new SleepingTask());
        }
        exec.shutdown();
    }

}
