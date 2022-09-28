package dahuashejimoshi.commandV2;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 17:02
 * @Description
 * @ClassName ConcreteCommand
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteCommand extends Command{

    public ConcreteCommand(Receiver receiver){
        super(receiver);
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
