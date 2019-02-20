package pattern.builder;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 鸡肉汉堡
 */
public class ChickenBurger extends Burger {

    /**
     * 实现具体的价格
     * @return
     */
    @Override
    public float price() {
        return 50.5f;
    }

    /**
     * 实现具体的名称
     * @return
     */
    @Override
    public String name() {
        return "Chicken Burger";
    }
}