package java7.class2.part3;

import java.lang.reflect.Proxy;

/**
 * @author zhaojiatao
 * @date 2019-09-21
 * 在开发新版本的时候发现旧版本的GreetV1不合理，才设计了V2版本
 * 在以后的代码中都要使用此版本，但是同时旧接口的代码也要能够继续使用。这就是动态代理可以发挥作用的地方。
 * 通过动态代理，可以把实现了旧接口GreetV1的对象实例转换成可以通过新接口GreetV2来调用。
 * 这样，既保证了对新接口的使用，又使旧接口的实现可以继续存在。
 * 实际上是通过动态代理实现适配器模式。
 */
public class GreetFactory {

    /**
     * 每一个GreetV1接口的实现，都可以通过此工厂方法转换成通过GreeV2接口来使用的新对象
     * @param greet
     * @return
     */
    public static GreetV2 adaptGreet(GreetV1 greet){
        GreetAdapter adapter=new GreetAdapter(greet);
        ClassLoader cl=greet.getClass().getClassLoader();
        return (GreetV2) Proxy.newProxyInstance(cl,new Class<?>[]{GreetV2.class},adapter);
    }

    public static void main(String[] args) {

        GreetV2 greetV2= adaptGreet(new GreetV1() {
            @Override
            public String greet(String name, String gender) throws RuntimeException {
                return name+gender;
            }
        });
        System.out.println( greetV2.greet("大山"));

    }
}
