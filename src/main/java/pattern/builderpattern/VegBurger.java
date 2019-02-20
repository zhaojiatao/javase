package pattern.builderpattern;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 蔬菜汉堡
 * 实际建造者
 */
public class VegBurger extends Burger {

    @Override
    public float price() {
        return 25.0f;
    }

    @Override
    public String name() {
        return "Veg Burger";
    }
}