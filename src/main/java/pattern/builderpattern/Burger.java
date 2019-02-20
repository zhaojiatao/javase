package pattern.builderpattern;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 汉堡抽象模型，继承自条目
 * 抽象类是抽象的建造者
 */
public abstract class Burger implements Item {

    /**
     * 所有的汉堡都有统一的默认包装：纸盒
     * @return
     */
    @Override
    public Packing packing() {
        return new Wrapper();
    }

    /**
     * 不同汉堡的价格不一样，待实现
     * @return
     */
    @Override
    public abstract float price();
}
