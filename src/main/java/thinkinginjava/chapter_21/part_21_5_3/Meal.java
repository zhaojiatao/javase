package thinkinginjava.chapter_21.part_21_5_3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhaojiatao
 * @date 2019-09-23
 */
public class Meal {

    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal"+orderNum;
    }
}


class WaitPerson implements Runnable{


    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                synchronized (this){
                    while (restaurant.meal==null){
                        wait();
                    }
                }
                System.out.println("Waitperson got "+restaurant.meal);
                synchronized (restaurant.chef){
                    restaurant.meal=null;
                    restaurant.chef.notifyAll();
                }

            }
        }catch (InterruptedException e){
            System.out.println("WaitPerson interrupted");
        }

    }
}


class Chef implements Runnable{
    private Restaurant restaurant;
    private int count=0;

    public Chef(Restaurant r) {
        this.restaurant = r;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                synchronized (this){
                    while (restaurant.meal!=null){
                        wait();
                    }
                }
                if(++count==10){
                    System.out.println("Out of food,closing");
                    restaurant.waitPerson.notifyAll();
                }
                System.out.println("Order up");
                synchronized (restaurant.waitPerson){
                    restaurant.meal=new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){

        }
    }
}


class Restaurant{

    Meal meal;

    ExecutorService exec= Executors.newCachedThreadPool();
    WaitPerson waitPerson=new WaitPerson(this);

    Chef chef=new Chef(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}