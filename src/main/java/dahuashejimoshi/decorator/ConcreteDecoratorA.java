package dahuashejimoshi.decorator;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/26 16:24
 * @Description
 * @ClassName ConcreteDecoratorA
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteDecoratorA extends Decorator{

    /**
     * 本类的独有功能，以区别于ConcreteDecoratorB
     */
    private String addedState;

    @Override
    public void operation() {
        super.operation();//首先执行原Component的Operation()，再执行本类的功能，如addedState，相当于对原Component进行了装饰
        addedState="New State";
        System.out.println("具体装饰对象A的操作");
    }
}
