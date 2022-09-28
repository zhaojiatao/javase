package dahuashejimoshi.command;

/**
 * 买股票命令
 * 与命令执行者股票类绑定
 */
public class BuyStockCommand implements Order {
   private StockCommandReceiver abcStockCommandReceiver;
 
   public BuyStockCommand(StockCommandReceiver abcStockCommandReceiver){
      this.abcStockCommandReceiver = abcStockCommandReceiver;
   }
 
   @Override
   public void execute() {
      abcStockCommandReceiver.buy();
   }
}