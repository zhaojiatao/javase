package pattern.Bridge;

/**
 * 抽象类
 * 抽象类与具体的实现类在结构上是相互独立的，两者的相互变化并不会影响到彼此，只要“桥梁”没变，两者的变化并不会影响到彼此。
 * @author zhaojiatao
 * @date 2019/3/11
 */
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }

    /**
     * 实现功能的抽象方法
     */
    public abstract void draw();
}