package dahuashejimoshi.builder;

/**
 * 建造者模式是在当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时适用的模式
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:55
 * @Description
 * @ClassName BuilderTestMain
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class BuilderTestMain {
    public static void main(String[] args) {
        //使用b1建造者或b2建造者可以建造出不同的结果，而每个建造者内部的建造过程都是稳定的
        Director director=new Director();
        Builder b1=new ConcreteBuilder1();
        Builder b2=new ConcreteBuilder2();

        director.construct(b1);
        Product result = b1.getResult();
        result.show();

        director.construct(b2);
        Product result2 = b2.getResult();
        result2.show();

    }
}
