package pattern.builder;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 创建实现 Packing 接口的实体类:瓶装
 */
public class Bottle implements Packing {

    @Override
    public String pack() {
        return "Bottle";
    }
}
