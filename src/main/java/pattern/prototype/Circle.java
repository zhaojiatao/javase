package pattern.prototype;

/**
 * @author zhaojiatao
 * @date 2019/2/20
 */
public class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}