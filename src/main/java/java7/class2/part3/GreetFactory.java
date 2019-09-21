package java7.class2.part3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 本篇幅代码主要讲述动态代理的实用案例：
 * @author zhaojiatao
 * @date 2019-09-21
 * 在开发新版本的时候发现旧版本的GreetV1不合理，才设计了V2版本
 * 在以后的代码中都要使用此版本，但是同时旧接口的代码也要能够继续使用。这就是动态代理可以发挥作用的地方。
 * 通过动态代理，可以把实现了旧接口GreetV1的对象实例转换成可以通过新接口GreetV2来调用。
 * 这样，既保证了对新接口的使用，又使旧接口的实现可以继续存在。
 * 实际上是通过动态代理实现适配器模式。
 * 实现这样的动态代理的关键就在于适配两个接口的InvocationHandler，见GreetAdapter
 */
public class GreetFactory {

    /**
     * 每一个GreetV1接口的实现，都可以通过此工厂方法转换成通过GreeV2接口来使用的新对象
     *
     * @param greet
     * @return
     */
    public static GreetV2 adaptGreet(GreetV1 greet) {
        GreetAdapter adapter = new GreetAdapter(greet);
        ClassLoader cl = greet.getClass().getClassLoader();
        return (GreetV2) Proxy.newProxyInstance(cl, new Class<?>[]{GreetV2.class}, adapter);
    }

    public static void main(String[] args) {

        GreetV1 greetV1 = new GreetV1Impl();

        GreetV2 greetV2 = adaptGreet(greetV1);

        System.out.println(greetV2.greet("大山"));

    }
}

interface GreetV1 {
    String greet(String name, String gender) throws RuntimeException;

}

class GreetV1Impl implements GreetV1{

    @Override
    public String greet(String name, String gender) throws RuntimeException {
        return name + gender;
    }
}

interface GreetV2 {
    String greet(String username);

}

class GreetAdapter implements InvocationHandler {

    private GreetV1 greetV1;

    public GreetAdapter(GreetV1 greetV1) {
        this.greetV1 = greetV1;
    }

    /**
     * 要理解这个invoke方法其实不是代理对象的执行，而是生成的代理实例的接口方法执行时触发的逻辑
     * @param proxy
     * @param method 代理实例执行了代理接口的哪个方法
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();

        if ("greet".equals(methodName)) {
            String username = (String) args[0];
            //name和gender这里演示的意思是通过username可以计算得出
            String name = username + "zhaojiatao";
            String gender = username + "男";
            Method greetMethodV1 = GreetV1.class.getMethod(methodName, new Class<?>[]{String.class, String.class});
            return greetMethodV1.invoke(greetV1, new Object[]{name, gender});
        } else {
            return method.invoke(greetV1, args);
        }
    }

}