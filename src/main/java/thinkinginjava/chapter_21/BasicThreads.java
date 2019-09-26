package thinkinginjava.chapter_21;

import thinkinginjava.chapter_21.part_21_2_1.Liftoff;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class BasicThreads {
    public static void main(String[] args) {
        Thread t=new Thread(new Liftoff());
        t.start();
        System.out.println("waiting for liftoff");
    }
}
