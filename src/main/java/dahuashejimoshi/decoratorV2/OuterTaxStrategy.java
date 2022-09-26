package dahuashejimoshi.decoratorV2;

@TaxTypeAnnotation(taxType = TaxType.OUTER)
class OuterTaxStrategy implements TaxStrategy {
    @Override public double calc(long amount) {
        final double taxRate = 0.2;  // 获取税率
        return amount / (1 + taxRate) * taxRate;
    }
}
