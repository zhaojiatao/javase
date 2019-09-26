package thinkinginjava.chapter_21_thread.part_21_4;

import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019/3/28
 * 检查中断
 *
 * 知识要点总结：
 * 1、t.interrupt();这个方法的目的是中断即将或正在阻塞的线程，如sleep；
 *    如果执行此方法后，线程还没有走到阻塞的逻辑的时候，是不会阻塞的，直到走到了会导致阻塞的方法时才会抛出异常。
 * 2、在run方法的循环中，执行t.interrupt()方法后如果当前循环一只没有遇到会导致阻塞的方法，则会将当前这遍循环跑完，在进行下一循环前推出循环。
 * 3、在上述第二种情况是，执行t.interrupt()方法后如果没有遇到会导致阻塞的方法，但是我们却还是想判断当前线程是否被执行了t.interrupt()方法，
 *    那么可以使用Thread.currentThread().isInterrupted()
 *    注意要和Thread.currentThread().interrupted()这个方法区分，interrupted()这个方法内部会复位线程的中断状态。
 *
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
                //point1 程序有可能在线程休眠的时候调用中断方法
                NeedsCleanup n1=new NeedsCleanup(1);
                try{
                    System.out.println("sleeping");
                    for(int i=1;i<2500;i++){
                        System.out.println(i);
                        if(i==1500){
                            if(Thread.currentThread().isInterrupted()){
                                System.out.println("当前线程已经被调用了interrupt方法");
                            }
                            System.out.println(Thread.currentThread().isInterrupted());

                            //判断是否被阻塞，并且复位
                            System.out.println("Thread.currentThread().isInterrupted():临时插入:"+Thread.currentThread().interrupted());
                            TimeUnit.MILLISECONDS.sleep(1);
                            System.out.println("复位后即使休眠阻塞也不会抛异常");
                            //再次将线程中断操作,如果没有这句，那么这个循环将一只进行下去，可以尝试将下面这句注释掉
                            //Thread.currentThread().interrupt();



                            //下面这个sleep方法会导致线程阻塞，如果执行sleep时当前线程已经被执行了中断，则会抛出异常，但是会先执行finally的n1.cleanup();方法
                            TimeUnit.MILLISECONDS.sleep(1);
                        }
                    }
                    System.out.println("Thread.currentThread().isInterrupted():point1:"+Thread.currentThread().isInterrupted());
                    TimeUnit.SECONDS.sleep(1);
                    //point2 程序有可能在执行大量科学计算的过程中调用中断方法，这个过程中调用中断方法的话线程会继续执行，直到当前循环这一遍循环结束
                    NeedsCleanup n2=new NeedsCleanup(2);
                    try{
                        System.out.println("Calculating");
                        for(int i=1;i<250000000;i++){
                            d=d+(Math.PI+Math.E)/d;
                        }
                        System.out.println("Thread.currentThread().isInterrupted():point2:"+Thread.currentThread().isInterrupted());
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
            System.out.println("Thread.currentThread().interrupted():"+Thread.currentThread().interrupted());
            System.out.println("Thread.currentThread().isInterrupted():"+Thread.currentThread().isInterrupted());
        }



    }
}

class InterruptingIdion{
    public static void main(String[] args) throws Exception{

        Thread t=new Thread(new Blocked3());
        t.start();
        TimeUnit.MILLISECONDS.sleep(new Integer(1));
        //注意，当你在线程在调用interrupt方法的时候，这个方法只在即将或已经进入阻塞操作时才会让线程中断。
        t.interrupt();
        TimeUnit.SECONDS.sleep(new Integer(2));
        System.out.println("main:"+t.interrupted());
    }

}












