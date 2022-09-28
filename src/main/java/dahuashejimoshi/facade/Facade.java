package dahuashejimoshi.facade;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:42
 * @Description
 * @ClassName Facade
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class Facade {
    SubSystemOne subSystemOne;
    SubSystemTwo subSystemTwo;
    SubSystemThree subSystemThree;
    SubSystemFour subSystemFour;

    public Facade(){
        subSystemOne=new SubSystemOne();
        subSystemTwo=new SubSystemTwo();
        subSystemThree=new SubSystemThree();
        subSystemFour=new SubSystemFour();
    }
    public void methodA(){
        subSystemOne.methodOne();
        subSystemTwo.methodTwo();
    }
    public void methodB(){
        subSystemThree.methodThree();
        subSystemFour.methodFour();
    }


    public static void main(String[] args) {
        Facade facade=new Facade();
        facade.methodA();
        facade.methodB();
    }
}
