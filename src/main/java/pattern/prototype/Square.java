package pattern.prototype;

/**
 * @author zhaojiatao
 * @date 2019/2/20
 */
public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}