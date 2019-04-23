package thinkinginjava.chapter_21.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/3/28
 */
class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int ident) {
        id=ident;
        System.out.println("NeedsCleanup "+id);
    }
    public void cleanup(){
        System.out.println("Cleaning up "+id);
    }


}


class Blocked3 implements Runnable{
    private volatile double d=0.0;

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                //point1
                NeedsCleanup n1=new NeedsCleanup(1);
                try{
                    System.out.println("sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    //point2
                    NeedsCleanup n2=new NeedsCleanup(2);
                    try{
                        System.out.println("Calculating");
                        for(int i=1;i<250000000;i++){
                            d=d+(Math.PI+Math.E)/d;
                        }
                        System.out.println("Finished time-consuming operation");
                    }finally {
                        n2.cleanup();
                    }
                }finally {
                    //cleanup意味着清理资源的必要性
                    n1.cleanup();
                }
            }
        }catch (InterruptedException e){
            System.out.println("Exiting via InterruptedException");
        }



    }
}

class InterruptingIdion{
    public static void main(String[] args) throws Exception{

        Thread t=new Thread(new Blocked3());
        t.start();
        TimeUnit.MILLISECONDS.sleep(new Integer(1002));
        t.interrupt();
    }

}












