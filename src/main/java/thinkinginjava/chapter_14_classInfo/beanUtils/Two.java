package thinkinginjava.chapter_14_classInfo.beanUtils;

import lombok.Data;

import java.util.List;

/**
 * @author zhaojiatao
 * @date 2018/10/25
 */
@Data
public class Two{
    public String field1;
    public String field2;
    public List<String> field3;
    public int field4;
    public Integer field5;
    public Three field6;
    public Integer field7;
    // one和two的字段名一样
    public int field8;

    private static final long field9=-676767676767L;


}