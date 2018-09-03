package thinkingInJava.chapter_12_exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/9/3
 *
 * java.lang.NoClassDefFoundError和java.lang.ClassNotfoundException是易混淆两个完全不同的类型
 * NoClassDefFoundError和ClassNotFoundException区别
 * 我们经常被java.lang.ClassNotFoundException和java.lang.NoClassDefFoundError这两个错误迷惑不清，
 * 尽管他们都与Java classpath有关，但是他们完全不同。NoClassDefFoundError发生在JVM在动态运行时，根据你提供的类名，
 * 在classpath中找到对应的类进行加载，但当它找不到这个类时，就发生了java.lang.NoClassDefFoundError的错误，
 * 而ClassNotFoundException是在编译的时候在classpath中找不到对应的类而发生的错误。
 * ClassNotFoundException比NoClassDefFoundError容易解决，是因为在编译时我们就知道错误发生，并且完全是由于环境的问题导致。
 * 而如果你在J2EE的环境下工作，并且得到NoClassDefFoundError的异常，而且对应的错误的类是确实存在的，这说明这个类对于类加载器来说，可能是不可见的。
 *
 *
 *
 *  NoClassDefFoundError解决示例
    当发生由于缺少jar文件，或者jar文件没有添加到classpath，或者jar的文件名发生变更会导致java.lang.NoClassDefFoundError的错误。
    当类不在classpath中时，这种情况很难确切的知道，但如果在程序中打印出System.getproperty(“java.classpath”)，可以得到程序实际运行的classpath
    运行时明确指定你认为程序能正常运行的 -classpath 参数，如果增加之后程序能正常运行，说明原来程序的classpath被其他人覆盖了。

    NoClassDefFoundError也可能由于类的静态初始化模块错误导致，当你的类执行一些静态初始化模块操作，如果初始化模块抛出异常，
    哪些依赖这个类的其他类会抛出NoClassDefFoundError的错误。如果你查看程序日志，会发现一些java.lang.ExceptionInInitializerError的错误日志，
    ExceptionInInitializerError的错误会导致java.lang.NoClassDefFoundError: Could not initialize class，如下面的代码示例：
 *
 */

class User{
    private static String USER_ID = getUserId();

    public User(String id){
        this.USER_ID = id;
    }
    private static String getUserId() {
        throw new RuntimeException("UserId Not found");
    }
}

public class NoClassDefFoundErrorDueToStaticInitFailure {
    public static void main(String args[]){

        List<User> users = new ArrayList<User>(2);

        for(int i=0; i<2; i++){
            try{
                users.add(new User(String.valueOf(i))); //will throw NoClassDefFoundError
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
}
