package dahuashejimoshi.commandV2;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 17:00
 * @Description
 * @ClassName Invoker
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand(){
        command.execute();
    }
}
