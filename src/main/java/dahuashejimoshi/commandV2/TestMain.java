package dahuashejimoshi.commandV2;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 17:04
 * @Description
 * @ClassName TestMain
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class TestMain {

    public static void main(String[] args) {
        //1、绑定具体的命令与具体的执行者
        Receiver r=new Receiver();
        Command c=new ConcreteCommand(r);
        Invoker i=new Invoker();
        i.setCommand(c);
        i.executeCommand();

    }

}
