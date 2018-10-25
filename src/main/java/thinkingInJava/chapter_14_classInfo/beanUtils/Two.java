package thinkingInJava.chapter_14_classInfo.beanUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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


}