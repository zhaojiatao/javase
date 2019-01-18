package thinkinginjava.chapter_12_exception;

/**
 * @author zhaojiatao
 * @date 2018/9/2
 *
 *
 * 这个类主要说明，当finally中有return和没有retrun，返回基本类型和饮用类型的区别；
 *
 * 当finally中有return时，最终一定从finally中返回；
 * 当finally中没有return时：
 *      当返回引用类型时：finally中对返回值的修改会覆盖返回值；
 *      当返回基本类型时：finally中对返回值的修改不会覆盖返回值；
 *
 */
public class Finally {

    public static void main(String[] args) {
        System.out.println(test());
        System.out.println(testPrimitive());
    }

    public static Student test() {
        Student result = new Student("Tom", 0);
        try {
            result.setAge(1);
            //System.out.println(1/0);
            return result;
        } catch (Exception e) {
            result.setAge(2);
            return result;
        } finally {
            result.setAge(3);       //引用类型的返回值，可被修改
            result=new Student("Kobe", 33);
            //return new Student("Kobe", 33);   //可以被“具体值”覆盖
        }

    }

    public static int testPrimitive() {
        int x = 0;
        try {
            x = 1;
            //System.out.println(1/0);
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;          //基本类型的返回值，不可被修改
            //return x;    //可以被“具体值”覆盖
        }

    }

    private static class Student {
        private String name;
        private int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
        @Override
        public String toString() {
            return "Student [name=" + name + ", age=" + age + "]";
        }
        public void setAge(int age) {
            this.age = age;
        }

    }
}