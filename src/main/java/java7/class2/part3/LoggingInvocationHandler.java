package java7.class2.part3;


import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author zhaojiatao
 * @date 2019-09-19
 */
@Data
public class LoggingInvocationHandler implements InvocationHandler {

    private Object receiverObject;

    public LoggingInvocationHandler(Object receiverObject) {
        this.receiverObject = receiverObject;
    }

    /**
     *
     * @param proxy 代理对象
     * @param method 所调用方法的Method对象
     * @param args 实际参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用方法"+method.getName()+":参数为"+ Arrays.deepToString(args));


        return method.invoke(receiverObject,args);
    }



}
