package pattern.Bridge;

/**
 * @author zhaojiatao
 * @date 2019/3/11
 * 具体的实现类
 */
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
