package dahuashejimoshi.command;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令执行器
 */
public class CommandBroker {
   private List<Order> orderList = new ArrayList<Order>(); 
 
   public void takeOrder(Order order){
      orderList.add(order);      
   }
 
   public void placeOrders(){
      for (Order order : orderList) {
         order.execute();
      }
      orderList.clear();
   }
}