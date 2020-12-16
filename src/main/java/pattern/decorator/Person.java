package pattern.decorator;

/**
 * 功能：
 *
 * @Author zhaojiatao
 * @Date 2020-12-16 14:26
 */

public class Person {
    private String name;
    public Person() {
    }
    public Person(String name) {
        this.name = name;
    }
    public void show(){
        System.out.println("原有的功能：装扮的"+name);
    }
}
