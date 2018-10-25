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

}