package dahuashejimoshi.observer;

public abstract class MyObserver {
   protected Subject subject;
   public abstract void update();
}