package pattern.Bridge;

/**
 * @author zhaojiatao
 * @date 2019/3/11
 * 具体的实现类
 * 注意并没有继承抽象类，而是继承桥梁接口
 */
public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
