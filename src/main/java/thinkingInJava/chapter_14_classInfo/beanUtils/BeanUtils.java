package thinkingInJava.chapter_14_classInfo.beanUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhaojiatao
 * @date 2018/10/25
 */
public class BeanUtils {

    public static final Map<String,Class> myPrimitiveTypeToWrapperMap=Maps.newHashMap();

    static {
        myPrimitiveTypeToWrapperMap.put("byte",java.lang.Byte.class);
        myPrimitiveTypeToWrapperMap.put("float",java.lang.Float.class);
        myPrimitiveTypeToWrapperMap.put("int",java.lang.Integer.class);
        myPrimitiveTypeToWrapperMap.put("long",java.lang.Long.class);
        myPrimitiveTypeToWrapperMap.put("char",java.lang.Character.class);
        myPrimitiveTypeToWrapperMap.put("double",java.lang.Double.class);
        myPrimitiveTypeToWrapperMap.put("short",java.lang.Short.class);
        myPrimitiveTypeToWrapperMap.put("boolean",java.lang.Boolean.class);
    }

    public static <T> T copyProperties(Object orig, T dest) {
        if (dest == null || orig == null) {
            return dest;
        }

        if (orig instanceof Map) {
            Map map = ((Map) orig);
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry next = (Map.Entry) iterator.next();
                for (Field field : dest.getClass().getDeclaredFields()) {
                    if (field.getName().equals(next.getKey())) {
                        Object value = next.getValue();
                        if (value == null) {
                            break;
                        }
                        if (field.getType().isInstance(value)) {
                            field.setAccessible(true);
                            try {
                                field.set(dest, value);
                            } catch (IllegalArgumentException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }

        } else /* if (orig is a standard JavaBean) */ {
            for (Field origField : orig.getClass().getDeclaredFields()) {
                Object value = null;
                try {
                    origField.setAccessible(true);
                    value = origField.get(orig);
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                if (value == null) {
                    continue;
                }
                for (Field field : dest.getClass().getDeclaredFields()) {
                    if (field.getName().equals(origField.getName())) {
                        try {

                            //如果目标字段和value对象是类实现关系
                            //数据源字段不管是基本类型还是包装类型，在value = origField.get(orig)时都会转为包装类，所以当目标字段是包装类时，走第一个判断
                            if (field.getType().isInstance(value)) {
                                field.setAccessible(true);
                                field.set(dest, value);
                            } else if (field.getType().isPrimitive()
                                    && Objects.equals(myPrimitiveTypeToWrapperMap.get(field.getType().getName()),value.getClass())) {
                                if( Modifier.isFinal(field.getModifiers())){
                                    //如果字段是final的
                                    continue;
                                }
                                //如果目标字段是基本类型，且数据源字段是目标字段的包装类时
                                field.setAccessible(true);
                                field.set(dest, value);
                            }



                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        return dest;
    }


    public static <T> T copyProperties(Object orig, Class<T> clazz) {
        if (orig == null) {
            return null;
        }
        T dest = null;
        try {
            dest = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
        return copyProperties(orig, dest);
    }

    public static <T> T deepCopyProperties(Object orig, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(orig), clazz);
    }

    public static <T> List<T> deepCopyListProperties(Object orig, Class<T> clazz) {
        return JSON.parseArray(JSON.toJSONString(orig), clazz);
    }

}
