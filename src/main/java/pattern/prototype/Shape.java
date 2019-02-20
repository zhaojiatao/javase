package pattern.prototype;

/**
 * @author zhaojiatao
 * @date 2019/2/20
 *  原型模式实现了一个原型接口，该接口用于创建当前对象的克隆。
    意图：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
    主要解决：在运行期建立和删除原型。
    何时使用： 1、当一个系统应该独立于它的产品创建，构成和表示时。 2、当要实例化的类是在运行时刻指定时，例如，通过动态装载。 3、为了避免创建一个与产品类层次平行的工厂类层次时。 4、当一个类的实例只能有几个不同状态组合中的一种时。建立相应数目的原型并克隆它们可能比每次用合适的状态手工实例化该类更方便一些。
    如何解决：利用已有的一个原型对象，快速地生成和原型对象一样的实例。
 */
public abstract class Shape implements Cloneable {

    private String id;
    protected String type;

    abstract void draw();

    public String getType(){
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}