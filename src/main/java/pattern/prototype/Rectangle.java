package pattern.prototype;

/**
 * @author zhaojiatao
 * @date 2019/2/20
 */
public class Rectangle extends Shape {
    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
