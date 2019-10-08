package thinkinginjava.chapter_19_enum.part_19_9;

import java.util.EnumMap;
import java.util.Map;

interface Command {
    void action();
}

enum AlarmPoints {
    classroom1, classroom2, classroom3, classroom4, classroom5, classroom6
}


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
