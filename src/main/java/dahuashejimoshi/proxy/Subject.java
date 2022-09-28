package dahuashejimoshi.proxy;

/**
 * Subject类，定义了RealSubject和Proxy的共用接口，这样就在任何使用RealSubject的地方都可以使用Proxy
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 9:40
 * @Description
 * @ClassName Subject
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public abstract class Subject {
    public abstract void Request();
}
