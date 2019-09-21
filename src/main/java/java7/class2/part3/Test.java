package java7.class2.part3;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojiatao
 * @date 2019-09-19
 */
public class Test {

    public static void useProxy() {
        String str = "Hello world";
        //创建动态代理时需要一个InvocationHandler接口的实现对象
        LoggingInvocationHandler handler = new LoggingInvocationHandler(str);

        Comparable proxy = makeProxy(Comparable.class, str, handler);
        int result = proxy.compareTo("Good");
        System.out.println(result);

        List<String> list = new ArrayList<String>();
        handler = new LoggingInvocationHandler(list);
        list = makeProxy(List.class, list, handler);
        list.add("Hello");
        System.out.println(JSON.toJSONString(list));

    }

    /**
     * 这是只有一个代理接口的生成代理的通用工厂方法
     *
     * 为任何object对象创建intf接口的代理
     * 大多数情况下，代理对象只会实现一个java接口
     *
     * 一句话概括就是
     * 生成 object对象 的intf接口 通过handler实现类处理方法逻辑的 代理实例对象
     *
     * @param intf    要代理的接口
     * @param object  代理对象
     * @param handler 处理接口方法调用的InvocationHandler接口实现类对象
     * @param <T>
     * @return 通过Proxy.newProxyInstance创建出来的代理对象只能转换成它所实现的接口类型，而不能转换成接口的具体实现类，因为jdk动态代理只对接口起作用
     * 一句话概括就是
     * 生成 object对象 的intf接口 通过handler实现类处理方法逻辑的 代理实例对象
     */
    public static <T> T makeProxy(Class<T> intf, final T object, InvocationHandler handler) {
        //代理对象的类加载器，这个类加载器主要是生成代理时用来反射用的
        ClassLoader cl = object.getClass().getClassLoader();
        return (T) Proxy.newProxyInstance(cl, new Class<?>[]{intf}, handler);
    }

    /**
     * 没有指定为代理对象指定具体哪个接口，而是为这个代理对象的所有接口生成通用的代理实例，用户可以自己根据需要转成自己想要的代理对象
     * @param object
     * @param handler
     * @return 并没有对创建的代理对象进行类型转换，而是直接返回给调用者。这是为了让调用者可以灵活操作，允许它们根据需要转换成不同的接口。
     * 比如，如果传入的是String类的对象实例，则调用者可以将其转换成String类所实现的Comparable或是CharSequence接口。
     */
    public static Object proxyAll(final Object object, InvocationHandler handler) {
        //代理对象的类加载器，这个类加载器主要是生成代理时用来反射用的
        ClassLoader cl = object.getClass().getClassLoader();
        Class<?>[] interfaces = object.getClass().getInterfaces();
        return Proxy.newProxyInstance(cl, interfaces, handler);
    }

    /**
     * 在同时代理多个接口时，这些接口在代理类创建时的排序非常重要，即使是同样的接口，不同的排序所产生的代理类也是不同的。
     * 强调接口的排序顺序的一个重要原因是，这个顺序会对接口中声明类型相同的方法的选择产生影响。
     * 如果多个接口中都存在声明类型相同的方法，那么在调用方法时，排列顺序中最先出现的接口中的方法会被选择。
     *
     * @throws Throwable
     */
    public static void proxyMultipleInterfaces() throws Throwable {
        List<String> receiverObj = new ArrayList<String>();
        ClassLoader cl = new Test().getClass().getClassLoader();
        LoggingInvocationHandler handler = new LoggingInvocationHandler(receiverObj);
        //首先获取到代理类，得到代理类实现了被代理的接口
        Class<?> proxyClass = Proxy.getProxyClass(cl, new Class<?>[]{List.class, Set.class});
//        Class<?> proxyClass=Proxy.getProxyClass(cl,new Class<?>[]{ Set.class,List.class});
        Object proxy = proxyClass.getConstructor(new Class[]{InvocationHandler.class}).newInstance(new Object[]{handler});

        List list = (List) proxy;
        list.add("hello");

        Set set = (Set) proxy;
        set.add("World");

    }

    public static void main(String[] args) throws Throwable {
//        useProxy();
        proxyMultipleInterfaces();
    }

}

class LoggingInvocationHandler implements InvocationHandler {

    private Object receiverObject;

    public LoggingInvocationHandler(Object receiverObject) {
        this.receiverObject = receiverObject;
    }

    /**
     * @param proxy  代理对象
     * @param method 所调用方法的Method对象
     * @param args   实际参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用方法" + method.getName() + ":参数为" + Arrays.deepToString(args));

        return method.invoke(receiverObject, args);
    }

}