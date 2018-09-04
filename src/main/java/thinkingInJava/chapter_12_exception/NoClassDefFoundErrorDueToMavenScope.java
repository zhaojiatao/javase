package thinkingInJava.chapter_12_exception;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojiatao
 * @date 2018/9/4
 *
 * 如果maven的scope是provided，则运行时会找不到class， java.lang.NoClassDefFoundError;
 * 如果把scope改为compile则可以正常运行
 */
public class NoClassDefFoundErrorDueToMavenScope {

    public static void main(String[] args) {
        Map map=new HashMap();
        map.put("a","a");
        map.put("b","b");
        System.out.println(JSON.toJSONString(map));



    }



}
