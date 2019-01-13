package thinkingInJava.chapter_21;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class DaemonDontRunFinally {
    public static void main(String[] args) {
        Thread t=new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
    }
}
