package thinkinginjava.chapter_21;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class SimpleDaemons implements Runnable{
    public void run(){
        try{
            while (true){
                TimeUnit.MILLISECONDS.sleep(700);
                System.out.println(Thread.currentThread()+""+this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<10;i++){
            Thread daemon=new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        TimeUnit.MILLISECONDS.sleep(2300);
    }

}
