package dahuashejimoshi.decorator;

import lombok.Data;

/**
 * 装饰抽象类
 * 继承了Component，从外部拓展Component类的功能，但对于Component来说，是无需知道Decorator的存在的。
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/26 16:22
 * @Description
 * @ClassName Decorator
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public abstract class Decorator extends Component{

    protected Component component;

    public void setComponent(Component component){
        this.component=component;
    }

    /**
     * 重写Operation方法，实际执行的是Component的Operation方法
     */
    @Override
    public void operation(){
        if(component!=null){
            component.operation();
        }
    }


}
