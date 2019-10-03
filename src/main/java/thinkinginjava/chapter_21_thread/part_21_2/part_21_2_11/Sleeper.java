package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_11;

/**
 * @author zhaojiatao
 * @date 2019/1/18
 */
public class Sleeper extends Thread{
    /**
     * 休眠时间
     */
    private int duration;

    /**
     * 通过构造方法可以传入当前休眠任务的名称和休眠时间
     * @param name
     * @param sleepTime
     */
    public Sleeper(String name,int sleepTime){
        super(name);
        duration=sleepTime;
        start();
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始执行");
        try {
            //休眠指定毫秒数
            sleep(duration);
        } catch (InterruptedException e) {
            //如果被中断则抛出异常
            //注意这里的isInterrupted总是为假，因为中断状态标志在被异常捕获时就被清空了。
            System.out.println(getName()+" was interrupted. "+"isInterrupted(): "+isInterrupted());
        }
        //打印线程醒来
        System.out.println(getName()+ " has awakened");
    }
}

/**
 * 插入的任务
 */
class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name,Sleeper sleeper){
        super(name);
        this.sleeper=sleeper;
        start();
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始执行");
        try {
            //哪个任务对象调用了join方法，哪个任务就插入进来先执行，当前任务挂起等待插入任务执行完毕再继续
            sleeper.join();
            System.out.println(getName()+"继续执行");
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println(getName()+" join completed");
    }
}

 class Joining{

    public static void main(String[] args) {
        //在构造方法中开始任务s
        Sleeper sleepy=new Sleeper("Sleepy",3000);
        Sleeper grumpy=new Sleeper("Grumpy",3000);
        Joiner dopey=new Joiner("Dopey",sleepy);
        Joiner doc=new Joiner("Doc",grumpy);
        //grumpy在休眠的时候被中断，则会抛异常
        grumpy.interrupt();
    }



}














