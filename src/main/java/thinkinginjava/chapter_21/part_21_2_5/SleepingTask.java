package thinkinginjava.chapter_21.part_21_2_5;

import thinkinginjava.chapter_21.part_21_2_1.Liftoff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 * 线程休眠
 */
public class SleepingTask extends Liftoff {

    @Override
    public void run() {
        try {

            while(countDown-->0){
                System.out.println(status());

                //当前线程睡眠会使当前线程从执行状态切换到阻塞状态，cpu执行的权利会让渡出来给其他线程。
                //当睡眠结束时，当前线程从阻塞状态转为可运行状态，等待cpu执行。
                TimeUnit.MILLISECONDS.sleep(100);
            }

        } catch (InterruptedException e) {
            //线程内异常无法跨线程传播
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
