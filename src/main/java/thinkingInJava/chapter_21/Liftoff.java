package thinkingInJava.chapter_21;

/**
 * @author zhaojiatao
 * @date 2019/1/12
 */
public class Liftoff implements Runnable{
    protected int countDown=10;//default
    private static int taskCount=0;
    private final int id=taskCount++;
    public Liftoff(){

    }
    public Liftoff(int countDown){
        this.countDown=countDown;
    }
    public String status(){
        return "#"+id+"("+
                (countDown>0?countDown:"Liftoff!")+"),";
    }
    public void run(){
        while(countDown-->0){
            System.out.println(Thread.currentThread().getName());
            System.out.print(status());
            Thread.yield();
        }
    }
}