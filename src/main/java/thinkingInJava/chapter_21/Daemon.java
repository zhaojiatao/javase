package thinkingInJava.chapter_21;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class Daemon implements Runnable{

    private Thread[] t=new Thread[10];
    public void run(){
        for(int i=0;i<10;i++){
            t[i]=new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("DaemonSpawn "+i+" started, ");
        }
        for(int i=0;i<t.length;i++){
            System.out.println("t["+i+"].isDaemon()="+
            t[i].isDaemon()+",");
        }
        while (true){
            Thread.yield();
        }
    }


}
