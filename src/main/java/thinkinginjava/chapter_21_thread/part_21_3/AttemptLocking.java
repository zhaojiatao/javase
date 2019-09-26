package thinkinginjava.chapter_21_thread.part_21_3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 */
public class AttemptLocking {
    private ReentrantLock lock=new ReentrantLock();
    public void untimed(){
        //尝试上锁
        boolean captured=lock.tryLock();
        try{
            System.out.println("tryLock():"+captured);
        }finally {
            //如果上锁成功则释放锁
            if(captured){
                lock.unlock();
            }
        }

    }

    public void timed(){
        boolean captured=false;
        try{
            //如果2秒内可以竞争到锁
            captured=lock.tryLock(2, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        try{
            System.out.println("tryLock(2, TimeUnit.SECONDS):"+captured);
        }finally {
            //如果上锁成功则释放锁
            if(captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final AttemptLocking al=new AttemptLocking();
        al.untimed();
        al.timed();
        //启动一个后台线程，将当前对象上锁
        new Thread(){
            {
                setDaemon(true);
            }
            @Override
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        //使用yield方法可以让main主线程暂时让出cpu控制权，进入就绪状态，使得后台线程机会获取锁。
        Thread.yield();
        al.untimed();
        al.timed();

    }
}
