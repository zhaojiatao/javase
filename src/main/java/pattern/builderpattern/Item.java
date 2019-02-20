package pattern.builderpattern;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 表示食物条目和食物包装的接口
 */
public interface Item {
    /**
     * 条目的名称待具体的实现类实现
     * @return
     */
    public String name();
    public Packing packing();
    public float price();
}
