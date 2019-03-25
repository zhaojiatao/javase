package thinkinginjava.chapter_14_classInfo.beanUtils;

import lombok.Data;

import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/10/25
 */
@Data
public class One{
    public String field1;
    public String field2;
    public List<String> field3;
    //field4 One中是基本类型 Two中也是基本类型
    public int field4;
    public Integer field5;
    public Three field6;
    //field7的类型one中是基本类型，two中是包装类型
    public int field7;
    // one和two的字段名一样
    public String field8;

    private static final long field9=-343434343434L;

}