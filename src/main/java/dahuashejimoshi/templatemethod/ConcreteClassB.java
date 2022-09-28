package dahuashejimoshi.templatemethod;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:06
 * @Description
 * @ClassName ConcreteClassA
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteClassB extends AbstractClass{
    @Override
    public void step1() {
        System.out.println("B 第1步具体实现");
    }

    @Override
    public void step2() {
        System.out.println("B 第2步具体实现");
    }
}
