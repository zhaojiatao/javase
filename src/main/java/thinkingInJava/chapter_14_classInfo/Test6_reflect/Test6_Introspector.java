package thinkingInJava.chapter_14_classInfo.Test6_reflect;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhaojiatao
 * @date 2018/9/13
 *
 * 内省:
 * http://www.cnblogs.com/peida/archive/2013/06/03/3090842.html
 * https://blog.csdn.net/u010445297/article/details/60967146
 *
 */
public class Test6_Introspector {

    public static void main(String[] args) {
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("peida");
        try {
            BeanInfoUtil.getProperty(userInfo, "userName");

            BeanInfoUtil.setProperty(userInfo, "userName");

            BeanInfoUtil.getProperty(userInfo, "userName");

            BeanInfoUtil.setPropertyByIntrospector(userInfo, "userName");

            BeanInfoUtil.getPropertyByIntrospector(userInfo, "userName");

            BeanInfoUtil.setProperty(userInfo, "age");
            //说明：BeanInfoUtil.setProperty(userInfo, "age");报错是应为age属性是int数据类型，
            //而setProperty方法里面默认给age属性赋的值是String类型。所以会爆出argument type mismatch参数类型不匹配的错误信息。

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    @Test
    public void testBeanUtils(){
        UserInfo userInfo=new UserInfo();
        try {
            BeanUtils.setProperty(userInfo, "userName", "peida");

            System.out.println("set userName:"+userInfo.getUserName());

            System.out.println("get userName:"+BeanUtils.getProperty(userInfo, "userName"));

            BeanUtils.setProperty(userInfo, "age", 18);
            System.out.println("set age:"+userInfo.getAge());

            System.out.println("get age:"+BeanUtils.getProperty(userInfo, "age"));

            System.out.println("get userName type:"+BeanUtils.getProperty(userInfo, "userName").getClass().getName());
            System.out.println("get age type:"+BeanUtils.getProperty(userInfo, "age").getClass().getName());

            PropertyUtils.setProperty(userInfo, "age", 8);
            System.out.println(PropertyUtils.getProperty(userInfo, "age"));

            System.out.println(PropertyUtils.getProperty(userInfo, "age").getClass().getName());

            PropertyUtils.setProperty(userInfo, "age", "8");
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }



}

class BeanInfoUtil {

    public static void setProperty(UserInfo userInfo, String userName){
        PropertyDescriptor propDesc= null;
        try {
            propDesc = new PropertyDescriptor(userName, UserInfo.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        Method methodSetUserName=propDesc.getWriteMethod();
        try {
            methodSetUserName.invoke(userInfo, "wong");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("set userName:"+userInfo.getUserName());
    }

    public static void getProperty(UserInfo userInfo, String userName){
        PropertyDescriptor proDescriptor = null;
        try {
            proDescriptor = new PropertyDescriptor(userName, UserInfo.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        Method methodGetUserName=proDescriptor.getReadMethod();
        Object objUserName= null;
        try {
            objUserName = methodGetUserName.invoke(userInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("get userName:"+objUserName.toString());
    }

    public static void setPropertyByIntrospector(UserInfo userInfo, String userName){
        BeanInfo beanInfo= null;
        try {
            beanInfo = Introspector.getBeanInfo(UserInfo.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
        if(proDescrtptors!=null&&proDescrtptors.length>0){
            for(PropertyDescriptor propDesc:proDescrtptors){
                if(propDesc.getName().equals(userName)){
                    Method methodSetUserName=propDesc.getWriteMethod();
                    try {
                        methodSetUserName.invoke(userInfo, "alan");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println("set userName:"+userInfo.getUserName());
                    break;
                }
            }
        }
    }

    public static void getPropertyByIntrospector(UserInfo userInfo, String userName){
        BeanInfo beanInfo= null;
        try {
            beanInfo = Introspector.getBeanInfo(UserInfo.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
        if(proDescrtptors!=null&&proDescrtptors.length>0){
            for(PropertyDescriptor propDesc:proDescrtptors){
                if(propDesc.getName().equals(userName)){
                    Method methodGetUserName=propDesc.getReadMethod();
                    Object objUserName= null;
                    try {
                        objUserName = methodGetUserName.invoke(userInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    System.out.println("get userName:"+objUserName.toString());
                    break;
                }
            }
        }
    }
}

class UserInfo {

    private long userId;
    private String userName;
    private int age;
    private String emailAddress;

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}