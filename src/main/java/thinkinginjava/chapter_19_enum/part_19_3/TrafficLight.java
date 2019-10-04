package thinkinginjava.chapter_19_enum.part_19_3;

/**
 * @author zhaojiatao
 * @date 2019-10-04
 * switch中使用枚举
 */
public class TrafficLight {
    Signal color = Signal.RED;

    public void change() {
        switch (color) {
            case RED:
                color = Signal.GREEN;
                break;
            case GREEN:
                color = Signal.YELLOW;
                break;
            case YELLOW:
                color = Signal.RED;
                break;
            default:
                throw new RuntimeException("颜色不对");
        }
    }

    @Override
    public String toString() {
        return "The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight t=new TrafficLight();
        for (int i = 0; i < 7; i++) {
            System.out.println(t);
            t.change();
        }
    }

}

enum Signal {
    GREEN, YELLOW, RED,
}