package thinkinginjava.chapter_21_thread.part_21_4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/2/2
 */
public class Count {

    /**
     * count类的count字段存储的就是总计数，代表公园的总人数
     * 每次被一个线程调用一次increment方法，总人数会加一
     * 不同的线程都在count对象上同步，所以increment方法线程安全
     */
    private int count=0;
    private Random rand=new Random(47);

    /**
     * 每调用一次increment就会总计数增加一次，线程安全
     * count字段是私有的，使用公共的increment和value方法来控制外部对count字段的访问
     * @return
     */
    public synchronized int increment(){
        int temp=count;
        if(rand.nextBoolean()){
            Thread.yield();
        }
        return (count=++temp);
    }

    /**
     * 返回总计数
     * @return
     */
    public synchronized int value(){
        return count;
    }
}

/**
 * 公园入口类
 */
class Entrance implements Runnable{
    private static Count count=new Count();
    /**
     * 静态变量存储所有的入口对象
     */
    private static List<Entrance> entrances=new ArrayList<>();
    /**
     * 每个线程对象有个对象的变量number，用来存储每个特定入口的参观量。
     * 最终各个线程的number变量的和应该等于Count类的count字段的值。用来实现对count的双重校验
     */
    private int number=0;
    /**
     * 每个线程对象的id
     */
    private final int id;
    /**
     * 全局可见的类静态变量，表示公园是否闭馆
     */
    private static volatile boolean canceled=false;

    /**
     * 闭馆方法
     */
    public static void cancel(){
        canceled=true;
    }

    /**
     * 每新建一个线程的时候，会在线程集合中添加本线程，为了在打印时区分当前线程是哪个线程s
     * @param id
     */
    public Entrance(int id){
        this.id=id;
        //每新建一个线程的时候，会在线程集合中添加本线程
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled){
            //当没有取消时，为当前线程对象的number数值加一，线程安全
            synchronized (this){
                ++number;
            }
            //计数器的总和字段加一
            System.out.println(this+" Total: "+count.increment());

            //最终，每个线程的number字段的和应该等于求和工具类count的count字段的值。
            try {
                //每个入口每个100毫秒进来一个人
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopping "+this);
    }
    public synchronized int getValue(){
        return number;
    }

    @Override
    public String toString() {
        return "Entrance "+id+": "+getValue();
    }
    public static int getTotalCount(){
        return count.value();
    }
    public static int sumEntrances(){
        int sum=0;
        for (Entrance entrance:entrances){
            sum += entrance.getValue();
        }
        return sum;
    }
}

class OrnametalGarden{
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        //shutdown方法执行后，已启动的线程会继续执行，直到下面的设置的超时之后。
        exec.shutdown();
        //如果所有的任务在超时之前全部结束，则返回true，否则返回false
        if(!exec.awaitTermination(250,TimeUnit.MILLISECONDS)){
            System.out.println("Some tasks were not terminated");
        }
        System.out.println("Total: "+Entrance.getTotalCount());
        //虽然任务都已经死掉了，但是类的静态变量entrances里还是在保留对象
        System.out.println("Sum of Entrances: "+Entrance.sumEntrances());
    }
}

