package thinkinginjava.chapter_21.part_21_5_4;

import thinkinginjava.chapter_21.Liftoff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

/**
 * @author zhaojiatao
 * @date 2019-09-25
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
                Liftoff rocket=rockets.take();
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
        System.out.println(msg);
        LiftOffRunner runner=new LiftOffRunner(queue);
        Thread t=new Thread(runner);
        t.start();
        for (int i=0;i<5;i++){
            runner.add(new Liftoff(5));
        }
        getKey("Press 'Enter' ("+msg+")");
        t.interrupt();
        System.out.println("Finished "+msg+" test");
    }

    public static void main(String[] args) {
        test("LinkedBlockingQueue",new LinkedBlockingDeque<Liftoff>());
        test("ArrayBlockingQueue",new ArrayBlockingQueue<Liftoff>(3));
        test("SynchronousQueue",new SynchronousQueue<Liftoff>());
    }


}




























