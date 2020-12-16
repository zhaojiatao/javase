package pattern.decorator;

/**
 * 功能：
 * 当对系统中原有的功能进行拓展时，可以考虑使用装饰模式。
 * 即不改变原有功能逻辑，而是在原有功能代码外添加包装层，在包装层添加额外的逻辑，可以包装多层。
 * spring框架中的过滤器就是这种层层包装的模式。
 *
 * @Author zhaojiatao
 * @Date 2020-12-16 15:13
 */

public class Test {


    public static void main(String[] args) {
        Person me=new Person("Mr Zhao");
        {
            //第一种穿搭顺序：TShirts->Sneakers->PoloShirts
            System.out.println("第一种装扮");
            TShirts tShirts=new TShirts();
            Sneakers sneakers=new Sneakers();
            PoloShirts poloShirts=new PoloShirts();
            tShirts.Decorate(me);
            sneakers.Decorate(tShirts);
            poloShirts.Decorate(sneakers);
            poloShirts.show();
        }

        {
            //第二种穿搭顺序：Sneakers->PoloShirts->TShirts
            System.out.println("第二种装扮");
            TShirts tShirts=new TShirts();
            Sneakers sneakers=new Sneakers();
            PoloShirts poloShirts=new PoloShirts();
            sneakers.Decorate(me);
            tShirts.Decorate(sneakers);
            poloShirts.Decorate(tShirts);


            poloShirts.show();
        }




    }

}
