package thinkinginjava.chapter_21_thread.part_21_2.part_21_2_9;

import java.util.concurrent.TimeUnit;


/**
 * @author zhaojiatao
 * @date 2019/1/14
 */
class InnerThread1 {

    private int countDown=5;
    private Inner inner;

    //如果除了Thread类的基本方法外想要拓展一些方法的时候，通过定义一个继承了Thread的内部类很有意义。
    private class Inner extends Thread{
        Inner(String name){
            super(name);
            start();
        }
        public void run(){
            try {
            while (true){
                System.out.println(this);
                if(--countDown==0){
                    sleep(1000);
                }else{
                    break;
                }
            }
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
        }
        public String toString(){
            return getName()+":"+countDown;
        }

    }

    public InnerThread1(String name){
        inner=new Inner(name);
    }

}


class InnerThread2 {
    private int countDown=5;
    private Thread t;
    public InnerThread2(String name){
        //一般情况下只是想使用Thread类的能力，所以可以直接定义一个匿名内部线程类，
        //赋值给一个Thread的变量，并在类内部可以使用Thread变量t直接调用Thread类的接口方法。
        t=new Thread(name){
            public void run(){
                try {
                    while (true){
                        System.out.println(this);
                        if(--countDown>=0){
                            sleep(1000);
                        }else{
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("sleep() interrupted");
                }
            }
            public String toString(){
                return getName()+":"+countDown;
            }
        };
        t.start();

    }

}

class InnerRunnable1{
    private int countDown=5;
    private Inner inner;
    //通过实现runnable接口来实现内部类，性质同InnerThread1。
    private class Inner implements Runnable{
        Thread t;
        Inner(String name){
            t=new Thread(this,name);
            t.start();
        }
        public void run(){
            try {
                while (true){
                    System.out.println(this);
                    if(--countDown>=0){
                        TimeUnit.MILLISECONDS.sleep(1000);
                    }else{
                        break;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        }
        public String toString(){
            return t.getName()+":"+countDown;
        }
    }
    public InnerRunnable1(String name){
        inner=new Inner(name);
    }
}

class InnerRunnable2{
    private int countDown=5;
    private Thread t;
    public InnerRunnable2(String name){
        //通过传入一个Runnable接口的实现类给Thread构造方法的形式直接定义一个匿名内部类给Thread变量t。
        //类似InnerThread2。
        t=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true){
                        System.out.println(this);
                        if(--countDown==0){
                            return;
                        }
                        TimeUnit.MILLISECONDS.sleep(1000);
                    }
                }catch (Exception e){
                    System.out.println("sleep() interrupted");
                }
            }
            public String toString(){
                return Thread.currentThread().getName()+": "+countDown;
            }
        },name);
        t.start();
    }
}

class ThreadMethod{
    private int countDown=5;
    private Thread t;
    private String name;
    public ThreadMethod(String name){
        this.name=name;
    }
    //在方法内部启动线程
    public void runTask(){
        if(t==null){
            t=new Thread(name){
                @Override
                public void run() {
                    try{
                        while (true){
                            System.out.println(this);
                            if(--countDown==0){
                                return;
                            }
                            sleep(1000);
                        }
                    }catch (Exception e){

                    }
                }

                @Override
                public String toString() {
                    return getName()+": "+countDown;
                }
            };
            t.start();
        }
    }

}

class ThreadVariations{
    public static void main(String[] args) {
        new InnerThread1("InnerThread1");
        new InnerThread2("InnerThread2");
        new InnerRunnable1("InnerRunnable1");
        new InnerRunnable2("InnerRunnable2");
        new ThreadMethod("ThreadMethod").runTask();
    }
}










