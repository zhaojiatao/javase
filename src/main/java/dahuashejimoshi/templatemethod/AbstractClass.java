package dahuashejimoshi.templatemethod;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:04
 * @Description
 * @ClassName AbstractClass
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
abstract class AbstractClass {
    public abstract void step1();
    public abstract void step2();

    public void templateMethod(){
        step1();
        step2();
    }



}
