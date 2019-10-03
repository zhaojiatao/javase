package thinkinginjava.chapter_19_enum.part_19_2;

/**
 * @author zhaojiatao
 * @date 2019-10-03
 * 演示覆盖enum的方法
 */
public enum SpaceShip {
    SCOUT,CARGO,TRANSPORT,CRUISER,BATTLESHIP,MOTHERSHIP;

    @Override
    public String toString() {
        String id=name();
        System.out.println(id);
        String lower=id.substring(1).toLowerCase();
        System.out.println(lower);
        System.out.println(id.charAt(0));
        return id.charAt(0)+lower;
    }

    public static void main(String[] args) {
        for (SpaceShip s:values()) {
            System.out.println(s);
        }
    }

}
