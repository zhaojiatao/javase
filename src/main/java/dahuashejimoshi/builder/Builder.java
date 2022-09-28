package dahuashejimoshi.builder;

/**
 * @author zhaojiatao
 * @version 1.0.0
 * @date 2022/9/28 10:51
 * @Description
 * @ClassName Builder
 * Copyright: Copyright (c) 2022-2023 All Rights Reserved.
 */
public abstract class Builder {
    public abstract void buildPartA();
    public abstract void buildPartB();
    public abstract Product getResult();

}
