package thinkinginjava.chapter_19_enum.part_19_2;

/**
 * @author zhaojiatao
 * @date 2019-10-03
 * 枚举不能继承
 * 可以向枚举类中添加方法，甚至可以有main()方法
 * 必须先定义枚举实例，否则会编译错误
 * 枚举类的构造方法不可以是public，因为其构造方法仅仅只能在enum定义的内部使用其构建enum实例。
 * 一旦enum定义结束，编译器就不允许我们再使用其构造器来创建任何实例了。
 */
public enum OzWitch {

    WEST("Miss Gulch,aka the Wicked Witch of the West"),
    NORTH("Glinda,the Good Witch of the North"),
    EASTS("Wicked Witch of the East,wearer of the Ruby Slippers,crushed by Dorothy's house"),
    SOUTH("Good by inference,but missing"),
    ;

    private String description;

    /**
     *
     * @param description
     */
    OzWitch(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void main(String[] args) {
        for (OzWitch witch : OzWitch.values()) {
            System.out.println(witch + ":" + witch.getDescription());
        }
    }

}
