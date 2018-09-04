package thinkingInJava.chapter_13_string;

import java.math.BigDecimal;

/**
 * @author zhaojiatao
 * @date 2018/9/4
 */
public class Test5_formatter {

    public static void main(String[] args) {
        BigDecimal a=new BigDecimal(16000);
        String b="8000";
        int c=2;
        System.out.println("成交价不能超过"+a+"元，即不能超过评估价"+b+"的"+c+"倍");

        System.out.println(String.format("成交价不能超过%.2f元，即不能超过评估价%s的%d倍",a,b,c));









    }


}
