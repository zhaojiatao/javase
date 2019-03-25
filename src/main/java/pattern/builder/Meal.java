package pattern.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2019/2/18
 * 一份套餐实体类
 * 监工
 */
public class Meal {
    /**
     * 一份套餐里包含多个条目
     */
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item){
        items.add(item);
    }

    /**
     * 汇总所有的条目的总价
     * @return
     */
    public float getCost(){
        float cost = 0.0f;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems(){
        for (Item item : items) {
            System.out.print("Item : "+item.name());
            System.out.print(", Packing : "+item.packing().pack());
            System.out.println(", Price : "+item.price());
        }
    }
}