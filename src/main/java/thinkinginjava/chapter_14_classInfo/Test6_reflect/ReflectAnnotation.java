package thinkinginjava.chapter_14_classInfo.Test6_reflect;

/**
 * @author zhaojiatao
 * @date 2018/9/16
 */

// 定义注解并指定java注解保留策略为运行时RUNTIME,运行时注入到JAVA字节码文件里
// 这样才可以在运行时反射并获取它。
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@interface MyAnnotation{
    String key() default "";
    int value()  default 0;
}

// 使用注解
@MyAnnotation(key="key1",value=200)
class MyClass{}


public class ReflectAnnotation {
    public static void main(String[] args) {
        MyClass myClass=new MyClass();
        MyAnnotation annotation=myClass.getClass().getAnnotation(MyAnnotation.class);
        System.out.println("key="+annotation.key()+"\tvalue="+annotation.value());
    }
}
