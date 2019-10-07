package thinkinginjava.chapter_19_enum.part_19_8;

import java.util.EnumSet;

/**
 * @author zhaojiatao
 * @date 2019-10-07
 *
 * EnumSet和hashset相比更快
 *
 */

/**
 * 表示一座大楼里警报传感器的安放位置
 */
public enum AlarmPoints {

    STAIR1,STAIR2,LOBBY,OFFICE1,OFFICE2,OFFICE3,OFFICE4,BATHROOM,UTILITY,KITCHEN

}

class EnumSets{

    public static void main(String[] args) {

        EnumSet<AlarmPoints> points=EnumSet.noneOf(AlarmPoints.class);
        points.add(AlarmPoints.BATHROOM);
        //[BATHROOM]
        System.out.println("1:"+points);
        points.addAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
        //[STAIR1, STAIR2, BATHROOM, KITCHEN]
        System.out.println("2:"+points);
        points=EnumSet.allOf(AlarmPoints.class);
        System.out.println("3:"+points);
        points.removeAll(EnumSet.of(AlarmPoints.STAIR1,AlarmPoints.STAIR2,AlarmPoints.KITCHEN));
        System.out.println("4:"+points);
        points.removeAll(EnumSet.range(AlarmPoints.OFFICE1,AlarmPoints.OFFICE4));
        System.out.println("5:"+points);
        points=EnumSet.complementOf(points);
        System.out.println("6:"+points);


    }


}
