package thinkinginjava.chapter_21_thread.part_21_5.part_21_5_5;


import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 本实例演示两个任务使用一个管道进行通信
 * 不过还是建议使用阻塞队列 即 blockQueue，更健壮更容易
 * @author zhaojiatao
 * @date 2019-09-29
 *
 * 通过输入/输出在线程间进行通信通常很有用。提供线程功能的类库以管道的形式对线程间的输入/输出提供了支持。
 * 他们在java输入/输出类库(IO类库)对应就是PipedWriter(允许任务向管道写)和PipedReader类(允许不同任务从同一个管道中读取)
 * 这个模型可以看成是生产者消费者问题的变体。这里的管道就是一个封装好的解决方案。
 * 管道基本上就是一个阻塞队列，存在于BlockingQueue之前的版本中。
 *
 */


public class Sender implements Runnable {

    private Random rand=new Random(47);

    /**
     * 创建了一个PipedWriter，他是一个单独的对象
     */
    private PipedWriter out=new PipedWriter();

    public PipedWriter getPipedWriter() {
        return out;
    }

    @Override
    public void run() {
        try{
            while (true){
                for (char c='A';c<'z';c++){
                    out.write(c);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                }
            }
        }catch (IOException e){
            System.out.println(e+" Sender write exception");
        }catch (InterruptedException ie){
            System.out.println(ie+" Sender sleep interrupted");
        }
    }
}

class Receiver implements Runnable{
    private PipedReader in;

    /**
     * PipedReader的建立必须与一个PipedWriter相关联。
     * @param sender
     * @throws IOException
     */
    public Receiver(Sender sender) throws IOException{
        this.in = new PipedReader(sender.getPipedWriter());
    }

    @Override
    public void run() {
        try{
            while (true){
                //Blocks until characters are there;
                //读操作如果没有更多的数据，管道会自动阻塞，无需自己手动sleep和wait
                System.out.println("Read: "+(char) in.read()+", ");
            }
        }catch (IOException e){
            System.out.println(e+" Receiver read exception");
        }
    }
}


class PipedIO{
    public static void main(String[] args) throws Exception {
        Sender sender=new Sender();
        Receiver receiver=new Receiver(sender);
        ExecutorService exec= Executors.newCachedThreadPool();
        exec.execute(sender);
        exec.execute(receiver);
        TimeUnit.SECONDS.sleep(4);
        exec.shutdownNow();
    }
}
















