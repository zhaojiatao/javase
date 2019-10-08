package thinkinginjava.chapter_19_enum.part_19_9;

import java.util.EnumMap;
import java.util.Map;

interface Command {
    void action();
}

enum AlarmPoints {
    classroom1, classroom2, classroom3, classroom4, classroom5, classroom6
}

/**
 * EnumMap 是一种 特殊的 Map ,要求其中的键(key)必须来自 enum,EnumMap 内部用数组实现,因此 查找速度非常快,
 * 下面的实例使用enum实例调用put()方法,其他的使用和普通的Map类似.下面这个例子使用命令设计模式的用法使用 EnumMap
 */
public class TestEnumMap {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> en = new EnumMap<>(AlarmPoints.class);
        en.put(AlarmPoints.classroom1, new Command() {

            @Override
            public void action() {
                System.out.println("this is classroom1");
            }
        });
        en.put(AlarmPoints.classroom1, new Command() {

            @Override
            public void action() {
                System.out.println("this is classroom1");
            }
        });
        en.put(AlarmPoints.classroom2, new Command() {

            @Override
            public void action() {
                System.out.println("this is classroom2");
            }
        });
        en.put(AlarmPoints.classroom3, new Command() {

            @Override
            public void action() {
                System.out.println("this is classroom3");
            }
        });
        for (Map.Entry<AlarmPoints, Command> e : en.entrySet()) {
            System.out.println(e.getKey());
            e.getValue().action();
        }

        // EnumMap 里没有这个key
        en.get(AlarmPoints.classroom4).action();
    }

}
