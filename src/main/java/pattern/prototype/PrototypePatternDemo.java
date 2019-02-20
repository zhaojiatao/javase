package pattern.prototype;

/**
 * @author zhaojiatao
 * @date 2019/2/20
 */
public class PrototypePatternDemo {
    public static void main(String[] args) {
        //模拟从数据库中执行了三次复杂查询，并构造了三个相对稳定的原型。并缓存在内存中
        ShapeCache.loadCache();

        //当每次从内存中以原型为模版拷贝新对象的时候，使用原型的clone方法
        Shape clonedShape = (Shape) ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());

        Shape clonedShape2 = (Shape) ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = (Shape) ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
    }
}
