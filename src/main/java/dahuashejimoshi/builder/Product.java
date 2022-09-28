package dahuashejimoshi.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:49
 * @Description
 * @ClassName Product
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public class Product {
    List<String> parts=new ArrayList<>();

    /**
     * 添加产品部件
     * @param part
     */
    public void add(String part){
        parts.add(part);
    }

    public void show(){
        System.out.println("产品 创建----");
        for (String part:parts) {
            System.out.println(part);
        }
    }

}
