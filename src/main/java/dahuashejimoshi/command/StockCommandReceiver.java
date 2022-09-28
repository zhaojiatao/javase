package dahuashejimoshi.command;

/**
 * 创建一个请求类。
 * 股票类
 * 即命令的执行者
 */
public class StockCommandReceiver {
   
   private String name = "ABC";
   private int quantity = 10;

   /**
    * 买入股票
    */
   public void buy(){
      System.out.println("Stock [ Name: "+name+", quantity: " + quantity +" ] bought");
   }

   /**
    * 卖出股票
    */
   public void sell(){
      System.out.println("Stock [ Name: "+name+", quantity: " + quantity +" ] sold");
   }
}