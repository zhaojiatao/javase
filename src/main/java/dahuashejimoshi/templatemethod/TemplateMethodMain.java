package dahuashejimoshi.templatemethod;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:07
 * @Description
 * @ClassName TemplateMethodMain
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class TemplateMethodMain {
    public static void main(String[] args) {
        AbstractClass c;
        c=new ConcreteClassA();
        c.templateMethod();

        c=new ConcreteClassB();
        c.templateMethod();
    }
}
