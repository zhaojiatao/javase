package pattern.builderpattern;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 可口可乐
 */
public class Coke extends ColdDrink {

    @Override
    public float price() {
        return 30.0f;
    }

    @Override
    public String name() {
        return "Coke";
    }
}
