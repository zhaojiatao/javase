package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_11;

/**
 * @author zhaojiatao
 * @date 2019/1/18
 */


/**
 join的意思是使得放弃当前线程的执行，并返回对应的线程，例如下面代码的意思就是：
 程序在main线程中调用t1线程的join方法，则main线程放弃cpu控制权，并返回t1线程继续执行直到线程t1执行完毕
 所以结果是t1线程执行完后，才到主线程执行，相当于在main线程中同步t1线程，t1执行完了，main线程才有执行的机会
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        ThreadJoinTest t1 = new ThreadJoinTest("A");
        ThreadJoinTest t2 = new ThreadJoinTest("B");
        t1.start();
        //join方法必须在线程start方法调用之后调用才有意义。这个也很容易理解：如果一个线程都没有start，那它也就无法同步了。
        t1.join(2000);
        /**
         * join方法中如果传入参数，则表示这样的意思：如果A线程中掉用B线程的join(10)，则表示A线程会等待B线程执行10毫秒，10毫秒过后，A、B线程并行执行。
         * 需要注意的是，jdk规定，join(0)的意思不是A线程等待B线程0秒，而是A线程等待B线程无限时间，直到B线程执行完毕，即join(0)等价于join()。
         */
        //t1.join(1);
        t2.start();
    }
}

class ThreadJoinTest extends Thread{
    public ThreadJoinTest(String name){
        super(name);
    }
    @Override
    public void run(){
        synchronized (currentThread()){
            for(int i=0;i<10;i++){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName() + ":" + i);
            }
        }

    }
}