package dahuashejimoshi.decoratorV2;

public class DecisionDemo {
    public static void main(String[] args) throws Exception {
        TaxStrategy taxStrategy = AnnotationTaxStrategyFactory.getTaxStrategy(TaxType.INTER);
        System.out.println(taxStrategy.calc(100));


        TaxStrategy taxStrategy2 = AnnotationTaxStrategyFactory.getTaxStrategy(TaxType.OUTER);
        System.out.println(taxStrategy2.calc(100));
    }
}
