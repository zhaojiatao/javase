package dahuashejimoshi.proxy;

/**
 * RealSubject定义Proxy所代表的真实实体
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 9:42
 * @Description
 * @ClassName RealSubject
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class RealSubject extends Subject{
    @Override
    public void Request() {
        System.out.println("真实的请求");
    }
}
