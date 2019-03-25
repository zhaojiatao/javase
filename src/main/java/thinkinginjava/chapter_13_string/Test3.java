package thinkinginjava.chapter_13_string;

/**
 * @author zhaojiatao
 * @date 2018/9/4
 */
public class Test3 {

    /*
    //注意:toString方法中，如果return=""+this，则this会自动转为string类型，递归调用toString方法，造成内存溢出；
    public String toString(){
        return "Test3"+this;
    }
    */

    //如果想打印内存地址，使用super.toString()即可；
    public String toString(){
        return "Test3"+super.toString();
    }


    public static void main(String[] args) {

        Test3 test3=new Test3();
        System.out.println(test3);

    }



}
