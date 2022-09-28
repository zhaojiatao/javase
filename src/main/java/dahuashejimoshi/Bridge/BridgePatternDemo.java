package dahuashejimoshi.Bridge;

/**
 * 使用相同的抽象类方法但是不同的桥接实现类，来画出不同颜色的圆。
 * @author zhaojiatao
 * @date 2019/3/11
 */
public class BridgePatternDemo {
    public static void main(String[] args) {
        //两个画圆具体实现类的不同点在于：长宽高可能不同、颜色可能不同
        Shape redCircle = new Circle(100,100, 10, new DrawRedCircle());
        Shape greenCircle = new Circle(100,100, 10, new DrawGreenCircle());
        //有相同的抽象行为画圆，但是具体的实现不同，一个画红色的一个画绿色的
        redCircle.draw();
        greenCircle.draw();
    }
}
