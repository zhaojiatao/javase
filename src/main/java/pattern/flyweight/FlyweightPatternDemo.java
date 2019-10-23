package pattern.flyweight;

/**
 * @author zhaojiatao
 * @date 2019-10-23
 * 享元模式，换句话说就是共享对象，在某些对象需要重复创建，且最终只需要得到单一结果的情况下使用。
 * 因为此种模式是利用先前创建的已有对象，通过某种规则去判断当前所需对象是否可以利用原有对象做相应修改后得到想要的效果，
 * 如以上教程的实例，创建了20个不同效果的圆，但相同颜色的圆只需要创建一次便可，相同颜色的只需要引用原有对象，改变其坐标值便可。
 * 此种模式下，同一颜色的圆虽然位置不同，但其地址都是同一个，所以说此模式适用于结果注重单一结果的情况。
 *
 * 举一个简单例子，一个游戏中有不同的英雄角色，同一类型的角色也有不同属性的英雄，
 * 如刺客类型的英雄有很多个，按此种模式设计，利用英雄所属类型去引用原有同一类型的英雄实例，
 * 然后对其相应属性进行修改，便可得到最终想得到的最新英雄；比如说你创建了第一个刺客型英雄，
 * 然后需要设计第二个刺客型英雄，你利用第一个英雄改变属性得到第二个刺客英雄，
 * 最新的刺客英雄是诞生了，但第一个刺客英雄的属性也随之变得与第二个相同，这种情况显然是不可以的。
 */
public class FlyweightPatternDemo {
    private static final String colors[] =
            { "Red", "Green", "Blue", "White", "Black" };
    public static void main(String[] args) {

        for(int i=0; i < 20; ++i) {
            Circle circle =(Circle)ShapeFactory.getCircle(getRandomColor());
            circle.setX(getRandomX());
            circle.setY(getRandomY());
            circle.setRadius(100);
            circle.draw();
        }
    }
    private static String getRandomColor() {
        return colors[(int)(Math.random()*colors.length)];
    }
    private static int getRandomX() {
        return (int)(Math.random()*100 );
    }
    private static int getRandomY() {
        return (int)(Math.random()*100);
    }
}