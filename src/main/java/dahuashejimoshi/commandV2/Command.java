package dahuashejimoshi.commandV2;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 16:59
 * @Description
 * @ClassName Command
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public abstract class Command {
    protected Receiver receiver;

    public Command(Receiver receiver){
        this.receiver=receiver;
    }
    abstract public void execute();
}
