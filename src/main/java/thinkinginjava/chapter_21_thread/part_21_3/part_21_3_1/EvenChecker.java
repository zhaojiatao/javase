package thinkinginjava.chapter_21_thread.part_21_3.part_21_3_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.yield;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 *
 * 一个任务生成偶数，而其他任务消费这些数字
 *
 * 消费者任务，将在随后的所有示例中被复用。消费者任务的唯一工作就是检查偶数的有效性。
 *
 */
public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EvenChecker (IntGenerator g,int ident){
        generator=g;
        id=ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()){
            int val=generator.next();
            if(val%2!=0){
                System.out.println(val+" not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator gp,int count){
        System.out.println("Press Control-C to exit");
        ExecutorService exec= Executors.newCachedThreadPool();
        for(int i=0;i<count;i++){
            exec.execute(new EvenChecker(gp,i));
        }
        exec.shutdown();
    }
    public static void test(IntGenerator gp){
        test(gp,10);
    }
}

/**
 * 为了将EvenChecker与我们要试验的各种类型的生成器解耦，我们将创建一个名为IntGenerator的抽象类。
 * 它包含EvenChecker必须了解的必不可少的方法：即一个next()方法，和一个可以执行撤销的方法，和一个可以执行撤销的方法。
 */
abstract class IntGenerator {
    /**
     * 为了保证可视性，此处使用volatile关键字
     */
    private volatile boolean canceled=false;
    public abstract int next();

    /**
     * 可以修改boolean类型的canceled标志的状态
     */
    public void cancel(){
        canceled=true;
    }

    /**
     * 可以查看该对象是否已经被取消
     * @return
     */
    public boolean isCanceled() {
        return canceled;
    }
}


class EvenGenerator extends IntGenerator {
    private int currentEvenValue=0;

    /**
     * 注意，危险点就在这里，这里两个++操作，如果在多线程并发执行时，无法保证在return 时签好只加了两次
     * 此外，就算只是递增操作，其实也不是原子性的。
     * 这里最好的处理办法就是加同步机制
     * @return
     */
    @Override
    public int next() {
        ++currentEvenValue;
        //此处防止yield将会更快让问题暴露
        yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
