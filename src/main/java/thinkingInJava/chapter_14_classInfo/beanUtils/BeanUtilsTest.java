package thinkingInJava.chapter_14_classInfo.beanUtils;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/10/25
 * org.apache.commons.beanutils.PropertyUtilsBean这个类主要就是通过java的反射机制来获得bean属性，只能够copy拥有public修饰符的类和方法。
 * spring的copyProperties使用了暴力反射
 * thinkingInJava.chapter_14_classInfo.beanUtils.BeanUtils.copyProperties也是使用了暴力反射
 */

public class BeanUtilsTest {

    @Test
    public void test01() throws Exception {
        One one=new One();
        one.setField1("field1");
        one.setField2("field2");
        one.setField3(Arrays.asList("field31","field32","field33"));
        one.setField4(10);
        one.setField5(new Integer(12));
        Three three=new Three();
        three.setC1("c1");
        three.setC2(new Integer(12));
        three.setC3(10);
        three.setC4(new java.util.Date());
        three.setC5(new java.sql.Date(2018,10,25));
        one.setField6(three);
        System.out.println(one.toString());
        System.out.println("=============");
        Two two1=new Two();
        org.apache.commons.beanutils.BeanUtils.copyProperties(two1,one);
        System.out.println(one.toString());
        System.out.println(two1.toString());
        System.out.println("=============");
        Two two2=new Two();
        org.apache.commons.beanutils.PropertyUtils.copyProperties(two2,one);
        System.out.println(one.toString());
        System.out.println(two2.toString());
        System.out.println("=============");
        Two two3=new Two();
        org.springframework.beans.BeanUtils.copyProperties(one,two3);
        System.out.println(one.toString());
        System.out.println(two3.toString());
        System.out.println("=============");
        Two two4=thinkingInJava.chapter_14_classInfo.beanUtils.BeanUtils.copyProperties(one,Two.class);
        System.out.println(one.toString());
        System.out.println(two4.toString());
    }



}



