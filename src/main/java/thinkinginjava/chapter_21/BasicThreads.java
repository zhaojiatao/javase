package thinkinginjava.chapter_21;

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
