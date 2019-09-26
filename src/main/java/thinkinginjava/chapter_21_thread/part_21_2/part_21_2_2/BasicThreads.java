package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_2;

import thinkinginjava.chapter_21_thread.part_21_2.part_21_2_1.Liftoff;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 *
 * 使用Thread驱动线程
 *
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t=new Thread(new Liftoff());
        t.start();
        System.out.println("waiting for liftoff");
    }
}

/**
 * 使用多个线程驱动任务
 */
class MoreBasicThreads {
    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            new Thread(new Liftoff()).start();
        }
        System.out.println("waiting for liftoff");
    }
}