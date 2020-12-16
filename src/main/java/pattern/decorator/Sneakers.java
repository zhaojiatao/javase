package pattern.decorator;

/**
 * 功能：
 *
 * @Author zhaojiatao
 * @Date 2020-12-16 15:05
 */

public class Sneakers  extends Finery{
    @Override
    public void show() {
        System.out.println("新增的功能：Sneakers");
        super.show();
    }
}