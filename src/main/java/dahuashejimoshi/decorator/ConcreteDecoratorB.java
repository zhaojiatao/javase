package dahuashejimoshi.decorator;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/26 16:24
 * @Description
 * @ClassName ConcreteDecoratorB
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteDecoratorB extends Decorator{


    private void addedBehavior(){
        System.out.println("B独有功能");
    }

    @Override
    public void operation() {
        super.operation();
        addedBehavior();
        System.out.println("具体装饰对象B的操作");
    }



}
