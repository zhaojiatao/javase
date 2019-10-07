package thinkinginjava.chapter_19_enum.part_19_8;

import java.util.EnumSet;

/**
 * @author zhaojiatao
 * @date 2019-10-07
 */
public enum AlarmPoints {

    STAIR1,STAIR2,LOBBY,OFFICE1,OFFICE2,OFFICE3,OFFICE4,BATHROOM,UTILITY,KITCHEN

}

class EnumSets{

    public static void main(String[] args) {

        EnumSet<AlarmPoints> points=EnumSet.noneOf(AlarmPoints.class);
        points.add(AlarmPoints.BATHROOM);
        System.out.println(points);
        points.addAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
        System.out.println(points);
        points=EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
        System.out.println(points);
        points.removeAll(EnumSet.range(AlarmPoints.OFFICE1,AlarmPoints.OFFICE4));
        System.out.println(points);
        points=EnumSet.complementOf(points);
        System.out.println(points);


    }


}
