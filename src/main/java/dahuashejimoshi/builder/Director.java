package dahuashejimoshi.builder;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:54
 * @Description
 * @ClassName Director
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class Director {
    /**
     * 抽象出稳定的建造步骤
     * @param builder
     */
    public void construct(Builder builder){
        builder.buildPartA();
        builder.buildPartB();
    }
}
