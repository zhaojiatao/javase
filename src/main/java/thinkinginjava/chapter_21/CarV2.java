package thinkinginjava.chapter_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhaojiatao
 * @date 2019-09-24
 *
 * 一般情况下 lock和condition对象是在更加困难的多线程问题中才是必需的。此处使用仅仅是和Car的实现的拓展。
 *
 */
public class CarV2 {
    private Lock lock = new ReentrantLock();
    /**
     * 使用互斥并允许挂起的基本类是Condition，用来在waitForWaxing和waitForBuffing中挂起线程。
     * 用来管理任务间的通信
     */
    private Condition condition = lock.newCondition();

    /**
     * 是否涂蜡
     */
    private boolean waxOn = false;

    /**
     * 涂蜡
     */
    public void waxed() {
        //每一个对lock()的调用都必须紧跟着一个try-finally子句，用来保证在所有情况下都可以释放锁。
        lock.lock();
        try {
            //设置是否涂蜡为是
            waxOn = true;
            //唤醒所有在这个锁上挂起的同步任务，与使用notifyAll相比signalAll是更安全的方式。
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 抛光
     */
    public void buffed(){
        lock.lock();
        try{
            //设置是否涂蜡为否
            waxOn=false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    /**
     * 等待涂蜡
     * @throws InterruptedException
     */
    public void waitForWaxing() throws InterruptedException{
        //获取锁
        lock.lock();
        try{
            //如果还没有涂蜡，则抛光线程挂起，等待涂蜡完成
            while (waxOn==false){
                //可以在condition上调用await来挂起一个任务
                condition.await();
            }
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    /**
     * 等待抛光
     * @throws InterruptedException
     */
    public void waitForBuffing() throws InterruptedException{
        lock.lock();
        try{
            while (waxOn==true){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }

}

/**
 * 涂蜡V2
 */
class WaxOnV2 implements Runnable{
    private CarV2 carV2;

    public WaxOnV2(CarV2 carV2) {
        this.carV2 = carV2;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                System.out.println("Wax on!");
                TimeUnit.MILLISECONDS.sleep(200);
                carV2.waxed();
                carV2.waitForBuffing();
            }

        }catch (InterruptedException e){
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax On task");
    }
}

/**
 * 抛光V2
 */
class WaxOffV2 implements Runnable{

    private CarV2 carV2;

    public WaxOffV2(CarV2 carV2) {
        this.carV2 = carV2;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                //等待涂蜡
                carV2.waitForWaxing();
                //涂蜡完成后开始抛光
                System.out.println("Wax Off!");
                TimeUnit.MILLISECONDS.sleep(200);
                carV2.buffed();
            }
        }catch (InterruptedException e){
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax Off task");
    }
}

class WaxOMatic2{

    public static void main(String[] args) throws InterruptedException {
        CarV2 carV2=new CarV2();
        ExecutorService exec= Executors.newCachedThreadPool();
        //抛光线程
        exec.execute(new WaxOffV2(carV2));
        //涂蜡线程
        exec.execute(new WaxOnV2(carV2));
        //主线程睡眠5秒钟后关闭线程
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }

}

























