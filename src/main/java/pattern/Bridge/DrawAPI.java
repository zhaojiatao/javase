package pattern.Bridge;

/**
 * 作为“桥梁”的接口
 * 按一般逻辑来说，我们是直接继承Shape来创建不同的具体对象，但桥接模式中是通过“桥梁”DrawAPI建立抽象与具体实现之间的联系，调用DrawAPI中的方法来具体实现。
 * @author zhaojiatao
 * @date 2019/3/11
 */
public interface DrawAPI {
    public void drawCircle(int radius, int x, int y);
}
