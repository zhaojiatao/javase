package pattern.decorator;

/**
 * 功能：
 *
 * @Author zhaojiatao
 * @Date 2020-12-16 14:29
 */

public class TShirts extends Finery{
    @Override
    public void show() {
        System.out.println("新增的功能：大T恤");
        super.show();
    }
}
