package thinkinginjava.chapter_21;

import thinkinginjava.chapter_21.part_21_2_1.Liftoff;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class MoreBasicThreads {
    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            new Thread(new Liftoff()).start();
        }
        System.out.println("waiting for liftoff");
    }
}
