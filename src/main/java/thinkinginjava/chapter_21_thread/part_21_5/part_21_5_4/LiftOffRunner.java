package thinkinginjava.chapter_21_thread.part_21_5.part_21_5_4;

import thinkinginjava.chapter_21_thread.part_21_2.part_21_2_1.Liftoff;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author zhaojiatao
 * @date 2019-09-25
 *
 * 使用同步队列解决任务协作问题
 * 同步队列在任何时刻都只允许一个任务插入或移除元素
 * BlockingQueue接口中提供了这个队列，这个接口有大量的标准实现。
 * 通常使用LinkedBlockingQueue，这是一个无界队列。
 * ArrayBlockingQueue 具有固定尺寸，因此可以在它被阻塞之前，向其中放置有限数量的元素。
 *
 * 如果消费者任务试图从队列中获取对象，而该队列此时为空，那么这些队列还可以挂起消费者任务，并且当有更多的元素可用时恢复消费者任务。
 * 阻塞队列可以解决非常大量的问题，而其方式与wait 、notifyAll相比，则简单并可靠得多。
 *
 * 下面是一个简单的测试，它将多个LiftOff对象的执行串行化了。
 * 消费者是LiftOffRunner，它将每个LiftOff对象从BlockingQueue中推出并直接运行。
 *
 *
 */
public class LiftOffRunner implements Runnable{

    private BlockingQueue<Liftoff> rockets;

    public LiftOffRunner(BlockingQueue<Liftoff> rockets) {
        this.rockets = rockets;
    }
    public void add(Liftoff lo){
        try{
            rockets.put(lo);
        }catch (InterruptedException e){
            System.out.println("Interrupted during put()");
        }
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                //将每个LiftOff对象从BlockingQueue中推出并直接运行。注意这里没有启动新线程，而是直接调用run方法了。
                //队列里面有可用的元素就取出，否则就阻塞
                Liftoff rocket=rockets.take();
                //执行任务
                rocket.run();
            }
        }catch (InterruptedException e){
            System.out.println("Waking from take()");
        }

        System.out.println("Exiting LiftOffRunner");
    }
}



class TestBlockingQueues{

    static void getKey(){
        try{
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }catch (java.io.IOException e){
            throw new RuntimeException(e);
        }
    }

    static void getKey(String message){
        System.out.println(message);
        getKey();
    }

    static void test(String msg, BlockingQueue<Liftoff> queue){
        System.out.println("msg:"+msg);
        //定义一个消费者任务并启动线程执行消费者任务，启动任务后，如果队列中是空的，则会阻塞
        LiftOffRunner runner=new LiftOffRunner(queue);
        Thread t=new Thread(runner);
        t.start();
        //放入队列五个待执行的任务
        for (int i=0;i<5;i++){
            runner.add(new Liftoff(5));
        }
        getKey("Press 'Enter' ("+msg+")");
        t.interrupt();
        System.out.println("Finished "+msg+" test");
    }

    public static void main(String[] args) {
        test("LinkedBlockingQueue",new LinkedBlockingQueue<Liftoff>());
        test("ArrayBlockingQueue",new ArrayBlockingQueue<Liftoff>(3));
        test("SynchronousQueue",new SynchronousQueue<Liftoff>());
    }


}




























