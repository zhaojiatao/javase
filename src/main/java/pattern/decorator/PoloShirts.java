package pattern.decorator;

/**
 * 功能：
 *
 * @Author zhaojiatao
 * @Date 2020-12-16 15:13
 */

public class PoloShirts  extends Finery{
    @Override
    public void show() {
        System.out.println("新增的功能：POLO衫");
        super.show();
    }
}

