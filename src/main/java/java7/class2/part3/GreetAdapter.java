package java7.class2.part3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhaojiatao
 * @date 2019-09-21
 */
public class GreetAdapter implements InvocationHandler {

    private GreetV1 greetV1;

    public GreetAdapter(GreetV1 greetV1) {
        this.greetV1 = greetV1;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName=method.getName();
        if("greet".equals(methodName)){
            String username= (String) args[0];
            String name= username+"zhaojiatao";
            String gender=username+"男";
            Method greetMethodV1=GreetV1.class.getMethod(methodName,new Class<?>[]{String.class,String.class});
            return greetMethodV1.invoke(greetV1,new Object[]{name,gender});
        }else {
            return method.invoke(greetV1,args);
        }
    }




}
