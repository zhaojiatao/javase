package pattern.builder;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 冷饮抽象模型，继承自条目
 */
public abstract class ColdDrink implements Item {

    /**
     * 冷饮的默认包装方式为瓶装
     * @return
     */
    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public abstract float price();
}
