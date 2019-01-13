package thinkingInJava.chapter_21;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class DaemonSpawn implements Runnable{
    public void run(){
        while (true){
            Thread.yield();
        }
    }
}
