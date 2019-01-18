package thinkinginjava.chapter_21;

import java.util.concurrent.ThreadFactory;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class DaemonThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread t=new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
