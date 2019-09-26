package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_9;

/**
 * @author zhaojiatao
 * @date 2019/1/14
 */
public class SimpleThread extends Thread{

    private int countDown=5;
    private static int threadCount=0;
    public SimpleThread(){
        super(Integer.toString(++threadCount));
        start();
    }

    @Override
    public String toString() {
        return "#"+getName()+"("+countDown+"),";
    }

    @Override
    public void run() {
        while (true){
            System.out.println(this);
            if (--countDown==0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            new SimpleThread();
        }
    }

}
