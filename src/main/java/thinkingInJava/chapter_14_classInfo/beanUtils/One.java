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
@ToString
public class One{
    public String field1;
    public String field2;
    public List<String> field3;
    public int field4;
    public Integer field5;
    public Three field6;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public List<String> getField3() {
        return field3;
    }

    public void setField3(List<String> field3) {
        this.field3 = field3;
    }

    public int getField4() {
        return field4;
    }

    public void setField4(int field4) {
        this.field4 = field4;
    }

    public Integer getField5() {
        return field5;
    }

    public void setField5(Integer field5) {
        this.field5 = field5;
    }

    public Three getField6() {
        return field6;
    }

    public void setField6(Three field6) {
        this.field6 = field6;
    }
}