package pattern.builderpattern;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 创建实现 Packing 接口的实体类:瓶装
 */
public class Wrapper implements Packing {

    @Override
    public String pack() {
        return "Wrapper";
    }
}
