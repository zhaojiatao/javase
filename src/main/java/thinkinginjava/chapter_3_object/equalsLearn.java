package thinkinginjava.chapter_3_object;

/**
 * @author zhaojiatao
 * @date 2018/7/19
 */
public class equalsLearn {

    public static void main(String[] args) {

        /**
         * 当基本类型的比较时：只能用==，比较值；
         * 当是引用类型比较时：如果未重写equals方法，那么equals用的是来自object类的equals方法，就是==，即比较内存地址
         * 当是String类型时，如果是由String str="xxx"；这样直接赋值的话，比较的是字符串的值；
         *                 如果由String str=new String("xxx")；方式new出来的对象的话，比较时是用的String类重写的equals方法；
         * 注意，toString方法返回的new String()对象；
         *
         */

        int x=1;
        int y=1;

        // 基本类型判断值是否相等
        System.out.println(x==y);// true

        //java会自动拆箱，由编译器完成，实际上判断的是x==1
        System.out.println(x==new Integer(1));// true

        //比较两个对象的内存地址
        System.out.println(new Integer(1)==new Integer(1));// false

        //使用Integer包装类重写的equals方法比较
        System.out.println(new Integer(1).equals(new Integer(1)));//true



        String str1="abc";
        String str2="abc";

        // 比较两个字符串的值
        System.out.println(str1==str2);// true

        // 比较两个字符串 使用String类重写的equeals方法
        System.out.println(str1.equals(str2));// true

        StringBuffer str3=new StringBuffer("efg");
        StringBuffer str4=new StringBuffer("efg");

        // 比较内存地址
        System.out.println(str3==str4);//false
        // 比较内存地址
        System.out.println(str3.equals(str4));// false 由于StringBuffer类没有重写Object类的equals，故比较的是内存地址

        // toString会生成new String()对象
        System.out.println(str3.toString()==str4.toString());//false 比较内存地址
        System.out.println(str3.toString().equals(str4.toString()));//true 使用String对象重写的equals比较

        System.out.println(111111);

    }


}
