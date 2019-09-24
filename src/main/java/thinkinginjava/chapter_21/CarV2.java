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
 */
public class CarV2 {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean waxOn = false;

    public void waxed() {
        lock.lock();
        try {
            waxOn = true;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void buffed(){
        lock.lock();
        try{
            waxOn=false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void waitForWaxing() throws InterruptedException{
        lock.lock();
        try{
            while (waxOn==false){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }

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

class WaxOffV2 implements Runnable{

    private CarV2 carV2;

    public WaxOffV2(CarV2 carV2) {
        this.carV2 = carV2;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                carV2.waitForWaxing();
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
        exec.execute(new WaxOffV2(carV2));
        exec.execute(new WaxOnV2(carV2));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }

}

























