package dahuashejimoshi.decorator;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/26 16:33
 * @Description
 * @ClassName DecortorMain
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class DecortorMain {

    public static void main(String[] args) {
        ConcreteComponent c=new ConcreteComponent();
        ConcreteDecoratorA d1=new ConcreteDecoratorA();
        ConcreteDecoratorB d2=new ConcreteDecoratorB();

        d1.setComponent(c);
        d2.setComponent(d1);
        d2.operation();

    }
}
