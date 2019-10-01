package thinkinginjava.chapter_21_thread.part_21_6;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 什么叫死锁：a在等待b，b等待c，c等待a，此时没有哪个线程能继续，这被称之为死锁。
 *
 * @author zhaojiatao
 * @date 2019-09-30
 *
 * 死锁的经典例证：哲学家就餐问题
 * 五个哲学家围成一团，哲学家会思考和就餐，当要就餐时必须同时得到左边和右边的筷子。
 * 如果一个哲学家左边或者右边已经有人在使用筷子了，那么这个哲学家就必须等待，直至可得到必须的筷子。
 *
 * 容易死锁的关键就在于这五个人是一团，第五个人的右边是第一个人的左边，当发生循环等待，就出现了死锁
 *
 */

/**
 * 筷子
 */
public class Chopstick {

    /**
     * 这个筷子是否正在被人占用
     */
    private boolean taken=false;

    /**
     * 拿起筷子
     * @throws InterruptedException
     */
    public synchronized void take() throws InterruptedException{
        //如果这个筷子正在被人占用则挂起当前线程，阻塞，释放当前对象锁，直到得到通知。
        while (taken){
            wait();
        }
        //当筷子没人使用的时候，退出while循环，设置taken为true。然后释放当前对象的锁，不过注意，此时并没用调用notify方法，等待的线程还会继续等待，直到调用drop方法，释放筷子。
        taken=true;
    }

    /**
     * 释放筷子
     */
    public synchronized void drop(){
        //设置筷子无人使用
        taken=false;
        //通知其他等待筷子的线程
        notifyAll();
    }

}

/**
 * 哲学家只是不断地思考和吃饭，ponderFactor会通过构造方法传入。如果ponderFactor不为零，则会pause休眠一段随机的时间。
 * 然后再尝试获取右边和左边的筷子。
 */
class Philosopher implements Runnable{
    /**
     * 左边的筷子
     */
    private Chopstick left;
    /**
     * 右边的筷子
     */
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand=new Random(47);

    /**
     * 随机花费一段时间，可能是在思考、吃饭
     * 思考的时间越短越容易死锁
     * @throws InterruptedException
     */
    private void pause() throws InterruptedException{
        if(ponderFactor==0){
            return;
        }
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor*250));
    }

    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                System.out.println(this+" "+"thinking");
                pause();
                //Philosopher becomes hungry
                System.out.println(this+" "+"grabbing right");
                right.take();
                System.out.println(this+" "+"grabbing left");
                left.take();
                System.out.println(this+" "+"eating" );
                pause();
                right.drop();
                left.drop();
            }
        }catch (InterruptedException e){
            System.out.println(this+" "+"exiting via interrupt");
        }
    }

    @Override
    public String toString() {
        return "Philosopher "+id;
    }
}

/**
 * 可能会产生死锁的版本
 */
class DeadlockingDiningPhilosophers{
    public static void main(String[] args) throws Exception {
        int ponder=5;
        if(args.length>0){
            ponder=Integer.parseInt(args[0]);
        }
        int size=5;
        if(args.length>1){
            size=Integer.parseInt(args[1]);
        }
        ExecutorService exec= Executors.newCachedThreadPool();
        Chopstick[] sticks=new Chopstick[size];
        for(int i=0;i<size;i++){
            exec.execute(new Philosopher(sticks[i],sticks[(i+1)%size],i,ponder));
        }
        if(args.length==3&&args[2].equals("timeout")){
            TimeUnit.SECONDS.sleep(5);
        }else {
            System.out.println("press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}



 class FixedDiningPhilosophers{
    public static void main(String[] args) throws Exception {
        int ponder=5;
        if(args.length>0){
            ponder=Integer.parseInt(args[0]);
        }
        int size=5;
        if(args.length>1){
            size=Integer.parseInt(args[1]);
        }
        ExecutorService exec=Executors.newCachedThreadPool();
        Chopstick[] sticks=new Chopstick[size];
        for (int i=0;i<size;i++){
            sticks[i]=new Chopstick();
        }
        for (int i=0;i<size;i++){
            if(i<(size-1)){
                exec.execute(new Philosopher(sticks[i],sticks[i+1],i,ponder));
            }else{
                exec.execute(new Philosopher(sticks[0],sticks[i],i,ponder));
            }
        }
        if(args.length==3&&args[2].equals("timeout")){
            TimeUnit.SECONDS.sleep(5);
        }else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}














































