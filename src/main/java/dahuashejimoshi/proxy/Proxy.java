package dahuashejimoshi.proxy;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 9:43
 * @Description
 * @ClassName Proxy
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class Proxy extends Subject{

    RealSubject realSubject;

    @Override
    public void Request() {
        if(realSubject==null){
            realSubject=new RealSubject();
        }
        realSubject.Request();
    }
}
