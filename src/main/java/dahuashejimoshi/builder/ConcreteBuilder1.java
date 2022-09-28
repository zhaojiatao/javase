package dahuashejimoshi.builder;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:52
 * @Description
 * @ClassName ConcreteBuilder1
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteBuilder1 extends Builder{
    private Product product=new Product();
    @Override
    public void buildPartA() {
        System.out.println("部件A");
    }

    @Override
    public void buildPartB() {
        System.out.println("部件B");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
