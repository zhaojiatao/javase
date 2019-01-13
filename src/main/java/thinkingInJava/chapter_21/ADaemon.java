package thinkingInJava.chapter_21;

/**
 * @author zhaojiatao
 * @date 2019/1/13
 */
public class ADaemon implements Runnable{
    public void run(){

        try{
            System.out.println("starting ADaemon");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("this should always run?");
        }

    }

}
