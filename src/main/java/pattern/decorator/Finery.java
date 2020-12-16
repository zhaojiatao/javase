package pattern.decorator;

/**
 * 功能：
 * @Author zhaojiatao
 * @Date 2020-12-16 14:28
 */

public class Finery extends Person{
    protected Person component;

    public void Decorate(Person component){
        this.component=component;
    }

    @Override
    public void show() {
        if(component!=null){
            component.show();
        }
    }
}
