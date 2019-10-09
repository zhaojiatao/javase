package thinkinginjava.chapter_19_enum.part_19_1;

enum Shrubbery{GROUND,CRAWLING,HANGING}


/**
 * @author zhaojiatao
 * @date 2019/10/02
 *
 */
public class EnumClass{

    public static void main(String[] args) {
        for (Shrubbery s:Shrubbery.values()){
            //xx.ordinal()方法表示每个enum实例在声明时的次序，从0开始
            System.out.println(s+" ordinal: "+s.ordinal());
            //枚举本身是由顺序的，此处的compareTo方法就是比较当前枚举和目标枚举CRAWLING的顺序先后，最左边的次序最小从0开始；
            //左边(前面)的序号小于右边的，则返回-1，相同返回0，大于返回1
            System.out.println(s.compareTo(Shrubbery.CRAWLING));
            //比较是否是一个枚举
            System.out.println(s.equals(Shrubbery.CRAWLING));
            //枚举可以使用==来比较
            System.out.println(s==Shrubbery.CRAWLING);
            //获取所属的枚举类
            System.out.println(s.getDeclaringClass());
            //name()方法返回enum实例声明时的名字
            System.out.println(s.name());
            System.out.println("-----------------");
        }

        for(String s:"HANGING CRAWLING GROUND".split(" ")){
            //valueOf是在Enum类中定义的static方法，它根据给定的名字返回相应的enum实例。如果不存在给定名字的实例，将会抛出异常。
            Shrubbery shrub=Enum.valueOf(Shrubbery.class,s);
            System.out.println(shrub);
        }

    }






}