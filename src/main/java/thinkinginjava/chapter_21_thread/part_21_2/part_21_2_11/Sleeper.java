package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_11;

/**
 * @author zhaojiatao
 * @date 2019/1/18
 */
public class Sleeper extends Thread{
    private int duration;
    public Sleeper(String name,int sleepTime){
        super(name);
        duration=sleepTime;
        start();
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始执行");
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName()+" was interrupted. "+"isInterrupted(): "+isInterrupted());
        }
        System.out.println(getName()+ " has awakened");
    }
}

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
        Sleeper sleepy=new Sleeper("Sleepy",3000);
        Sleeper grumpy=new Sleeper("Grumpy",3000);
        Joiner dopey=new Joiner("Dopey",sleepy);
        Joiner doc=new Joiner("Doc",grumpy);
        grumpy.isInterrupted();
    }



}














