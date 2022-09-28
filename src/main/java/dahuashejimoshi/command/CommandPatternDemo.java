package dahuashejimoshi.command;

public class CommandPatternDemo {
   public static void main(String[] args) {
      //股票买卖动作实际执行者
      StockCommandReceiver abcStockCommandReceiver = new StockCommandReceiver();
      //买股票命令
      BuyStockCommand buyStockCommandOrder = new BuyStockCommand(abcStockCommandReceiver);
      //卖股票命令
      SellStockCommand sellStockCommandOrder = new SellStockCommand(abcStockCommandReceiver);

      //先登记买卖命令，这里是集合
      CommandBroker commandBroker = new CommandBroker();
      commandBroker.takeOrder(buyStockCommandOrder);
      commandBroker.takeOrder(sellStockCommandOrder);

      //统一执行
      commandBroker.placeOrders();
   }
}