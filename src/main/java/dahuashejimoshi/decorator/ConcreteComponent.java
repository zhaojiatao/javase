package dahuashejimoshi.decorator;

/**
 * 定义一个具体的对象，也可以该这个对象添加一些功能
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/26 16:19
 * @Description
 * @ClassName ConcreteComponent
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class ConcreteComponent extends Component{
    @Override
    public void operation() {
        System.out.println("原始逻辑中具体对象的操作");
    }
}
