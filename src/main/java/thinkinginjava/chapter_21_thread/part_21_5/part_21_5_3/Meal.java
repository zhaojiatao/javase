package thinkinginjava.chapter_21_thread.part_21_5.part_21_5_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-09-23
 *
 * 生产者消费者问题
 *
 * 这个仅仅是一个简单的模型
 * 在复杂的生产者消费者问题上，一般都是使用先进先出队列来实现
 */
public class Meal {

    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal"+orderNum;
    }
}

/**
 * 服务员
 */
class WaitPerson implements Runnable{


    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {

                //本实例为了简单只在这一个同步方法上上锁，并等待通知。但实际上往往会有多个任务在某个特定对象锁上等待，因此，调用notifyAll更安全一些。
                synchronized (this){
                    while (restaurant.meal==null){
                        //等待notifyAll唤醒
                        wait();
                    }
                }
                System.out.println("Waitperson got "+restaurant.meal);
                synchronized (restaurant.chef){
                    restaurant.meal=null;
                    restaurant.chef.notifyAll();
                }

            }
        }catch (InterruptedException e){
            System.out.println("WaitPerson interrupted");
        }

    }
}

/**
 * 厨师
 */
class Chef implements Runnable{
    private Restaurant restaurant;
    private int count=0;

    public Chef(Restaurant r) {
        this.restaurant = r;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){

                synchronized (this){

                    //一定要注意使用这种while里面直接包含wait的惯用法，因为你无法保证在唤醒时，其他任务可能会修改判断的条件。
                    while (restaurant.meal!=null){
                        //一旦厨师送上菜并通知服务员后，这个厨师就将等待。直到厨师收集到订单并通知厨师，这样厨师就可以继续烧下一份菜了。
                        wait();
                    }
                }

                if(++count==10){
                    System.out.println("Out of food,closing");

                    //这个调用，会使得此时正处于阻塞状态的服务员抛出异常并结束。厨师会继续向下走，走到sleep的时候，在进入阻塞时抛异常。
                    //如果没有sleep，则返回while判断也同样可以跳出循环退出。
                    restaurant.exec.shutdownNow();
                }

                System.out.println("Order up");

                //注意此处厨师对notifyAll的调用必须现获取到服务员的锁，而服务员在调用wait的时候释放了这个锁，所以此处是可以得到的。
                //可以保证两个试图在同一个对象上调用notifyAll的任务不会相互冲突
                synchronized (restaurant.waitPerson){
                    restaurant.meal=new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){

        }
    }
}

/**
 * 饭店有一个厨师和一个服务员
 * 服务员必须等待厨师准备好膳食
 * 当厨师准备好时，他会通知服务员，服务员上菜，然后返回继续等待。
 * 厨师代表生产者
 * 服务员代表消费者
 * 两个任务必须咋爱膳食被生产和消费时进行握手，而系统必须以有序的方式关闭。
 */
class Restaurant{

    Meal meal;

    ExecutorService exec= Executors.newCachedThreadPool();

    WaitPerson waitPerson=new WaitPerson(this);

    Chef chef=new Chef(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}