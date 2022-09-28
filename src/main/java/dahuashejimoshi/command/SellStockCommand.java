package dahuashejimoshi.command;

public class SellStockCommand implements Order {
   private StockCommandReceiver abcStockCommandReceiver;
 
   public SellStockCommand(StockCommandReceiver abcStockCommandReceiver){
      this.abcStockCommandReceiver = abcStockCommandReceiver;
   }
 
   @Override
   public void execute() {
      abcStockCommandReceiver.sell();
   }
}