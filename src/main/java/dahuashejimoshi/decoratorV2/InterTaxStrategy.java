package dahuashejimoshi.decoratorV2;

@TaxTypeAnnotation(taxType = TaxType.INTER)
class InterTaxStrategy implements TaxStrategy {
    @Override public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount * taxRate;
    }
}