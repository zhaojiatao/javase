package java7.class2.part3;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2019-09-19
 */
public class Test {

    public static void useProxy(){
        String str="Hello world";
        //创建动态代理时需要一个InvocationHandler接口的实现对象
        LoggingInvocationHandler handler=new LoggingInvocationHandler(str);

        Comparable proxy=makeProxy(Comparable.class,str,handler);
        int result = proxy.compareTo("Good");
        System.out.println(result);

        List<String> list=new ArrayList<String>();
        handler=new LoggingInvocationHandler(list);
        list=makeProxy(List.class,list,handler);
        list.add("Hello");
        System.out.println(JSON.toJSONString(list));

    }

    /**
     * 通用的工厂方法：为任何object对象创建intf接口的代理
     * 大多数情况下，代理对象只会实现一个java接口
     * @param intf 要代理的接口
     * @param object 代理对象
     * @param handler 处理接口方法调用的InvocationHandler接口实现类对象
     * @param <T>
     * @return
     * 通过Proxy.newProxyInstance创建出来的代理对象只能转换成它所实现的接口类型，而不能转换成接口的具体实现类，因为动态代理只对接口起作用
     */
    public static <T> T makeProxy(Class<T> intf,final T object,InvocationHandler handler){
        ClassLoader cl=object.getClass().getClassLoader();
        return (T)Proxy.newProxyInstance(cl,new Class<?>[]{intf},handler);
    }

    public static void main(String[] args) {
        useProxy();
    }



}
