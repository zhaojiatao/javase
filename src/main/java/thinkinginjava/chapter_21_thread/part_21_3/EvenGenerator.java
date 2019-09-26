package thinkinginjava.chapter_21_thread.part_21_3;

/**
 * @author zhaojiatao
 * @date 2019/1/20
 */
public class EvenGenerator extends IntGenerator {
    private int currentEvenValue=0;

    @Override
    public int next() {
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
