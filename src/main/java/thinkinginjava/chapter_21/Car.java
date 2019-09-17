package thinkinginjava.chapter_21;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-09-17
 * 总结：
 * 线程之间有时需要协作来完成。
 * 1、wait()和notifyAll()
 *    wait方法会释放锁，sleep方法不会释放锁。
 *    wait()和notifyAll()必须在同步方法中使用。
 *    在调用wait的时候，一般都会在外面用while来包裹。
 */
public class Car {

    /**
     * 是否已经完成涂蜡
     */
    private boolean waxOn=false;

    /**
     * 执行涂蜡，修改涂蜡状态为已涂蜡，并唤醒当前对象的所有的因wait挂起的线程
     */
    public synchronized void waxed(){
        waxOn=true;
        notifyAll();
    }

    /**
     * 执行抛光，修改涂蜡状态为未涂蜡，并唤醒当前对象的所有的因wait挂起的线程
     */
    public synchronized void buffed(){
        waxOn=false;
        notifyAll();
    }

    /**
     * 等待涂蜡，如果没有涂蜡则当前线程挂起，释放当前同步方法占用的对象的锁。
     * @throws InterruptedException
     */
    public synchronized void waitForWaxing() throws InterruptedException{
        //一定要用while包裹住wait方法，因为wait方法挂起后再次执行的时候，你无法保证是否由其他和你一样的任务抢先执行了某些操作并修改了状态。
        while (waxOn==false){
            //调用wait方法时，线程被挂起，而锁被释放。这个是本质所在！！
            wait();
        }
    }

    /**
     * 等待抛光完成，如果还是处于涂蜡完成状态则当前线程挂起，释放当前同步方法占用的对象的锁。
     * @throws InterruptedException
     */
    public synchronized void waitForBuffing() throws InterruptedException{
        //一定要用while包裹住wait方法，因为wait方法挂起后再次执行的时候，你无法保证是否由其他和你一样的任务抢先执行了某些操作并修改了状态。
        while (waxOn==true){
            wait();
        }
    }


}

/**
 * 涂蜡
 */
class WaxOn implements Runnable{
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                System.out.println("Wax On!");
                //调用sleep模拟需要涂蜡的时间
                TimeUnit.MILLISECONDS.sleep(200);
                //告知汽车涂蜡结束
                car.waxed();
                //等待抛光，挂起线程
                car.waitForBuffing();
            }
        }catch (InterruptedException e){
            System.out.println("Exiting via interrupt 涂蜡任务中断");
        }
        System.out.println("Ending Wax On task 涂蜡任务结束");
    }
}

/**
 * 抛光
 */
class WaxOff implements Runnable{
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                //等待这个涂蜡完成，线程挂起，直至涂蜡完成被唤醒
                car.waitForWaxing();

                System.out.println("Wax off!");
                TimeUnit.MILLISECONDS.sleep(200);

                //执行抛光
                car.buffed();
            }
        }catch (InterruptedException e){
            System.out.println("Exiting via interrupt 抛光任务中断");
        }
        System.out.println("Ending Wax off task，抛光任务结束");
    }
}

/**
 * 涂蜡和抛光两个线程之间的协作示例
 */
 class WaxOMatic{
    public static void main(String[] args) throws Exception {
        Car car =new Car();
        ExecutorService exec= Executors.newCachedThreadPool();
        //任务1将抛光它。抛光任务在涂蜡任务完成之前，是不能执行其工作的。
        exec.execute(new WaxOff(car));
        //任务2将蜡涂到car上。涂蜡任务在涂两一层蜡之前，必须等待抛光任务完成。
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        //会调用由exec控制的线程的interrupt()方法
        exec.shutdownNow();

    }
}













