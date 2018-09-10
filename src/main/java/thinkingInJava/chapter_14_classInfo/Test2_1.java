package thinkingInJava.chapter_14_classInfo;

/**
 * @author zhaojiatao
 * @date 2018/9/10
 */
public class Test2_1 {
}


class Initable{
    static final int staticFinal=47;
    static final int staticFinal2=(int)(1+Math.random()*(10-1+1));
    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2{
    static int staticNonFinal=74;
}