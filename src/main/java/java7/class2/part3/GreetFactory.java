package java7.class2.part3;

import java.lang.reflect.Proxy;

/**
 * @author zhaojiatao
 * @date 2019-09-21
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
