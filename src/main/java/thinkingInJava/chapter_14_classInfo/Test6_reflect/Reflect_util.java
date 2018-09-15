package thinkingInJava.chapter_14_classInfo.Test6_reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhaojiatao
 * @date 2018/9/15
 *
 * 反射工具类所实现的功能内容包括：获取类所有的属性(包括从父类继承的)，直接通过反射获取、设置对象属性的值，
 * 反射调用类的静态方法，通过反射调用方法，查找唯一被指定注释声明的域，获得指定注释声明的字段属性、方法，获取全部使用指定注释声明的get方法。
 */
public class Reflect_util {
}


public class ReflectionUtil  extends ReflectionUtils{

    /**
     * 类反射属性缓存  key 类名  value—类属性<key: ,value:字段属性>
     */
    private static final Map<Class<?>, Map<String, Field>> fieldCache = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

    /**
     * 类反射方法缓存 key-类名 value-类属性<key: , value:方法属性>
     */
    private static final Map<Class<?>, Map<String, Method>> methodCache = new ConcurrentHashMap<Class<?>, Map<String, Method>>();

    /**	getFields()获得某个类的所有的公共（public）的字段，包括父类。
     getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
     同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()
     */
    /**
     * 获取类所有的属性(包括从父类继承的)
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Field[] getAllDeclareFields(Class clazz){
        if (clazz.getSuperclass() == Object.class) {//父类就是顶级类
            return clazz.getDeclaredFields();
        }

        Set<String> names = new HashSet<String>();
        List<Field> list = new ArrayList<Field>();
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if (names.contains(field.getName())) {
                    continue;
                }
                list.add(field);//字段属性
            }
            searchType = searchType.getSuperclass();//父类
        }
        //把list转化为数组形式返回
        Field[] fields = new Field[list.size()];
        return list.toArray(fields);

    }
    /**
     * 判断某字符串是否为空或长度为0或由空白符(whitespace) 构成
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 直接通过反射获取对象属性的值
     *
     * @param target
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object target, String fieldName) {
        if (target == null) {
            return null;
        }
        if (isBlank(fieldName)) {
            return null;
        }
        //Map直接返回
        if (target instanceof Map) {
            return ((Map<?, ?>) target).get(fieldName);
        }
        Class<?> clazz = null;
        if (target instanceof Class) {
            clazz = (Class<?>) target;
        }else {
            clazz = target.getClass();
        }

        Map<String, Field> fieldMap = fieldCache.get(clazz);
        if (fieldMap == null || fieldMap.containsKey(fieldName)) {
            synchronized (clazz) {
                fieldMap = fieldCache.get(clazz);
                if (fieldMap == null || fieldMap.containsKey(fieldName)) {
                    Field field = findField(clazz, fieldName);
                    if (field != null) {
                        field.setAccessible(true);
                        if (fieldMap == null) {
                            fieldMap = new ConcurrentHashMap<String, Field>();
                            fieldCache.put(clazz, fieldMap);
                        }
                    }
                }
            }
        }

        if(fieldMap == null || !fieldMap.containsKey(fieldName)){
            throw new RuntimeException(String.format("类 [%s] 不存在属性 [%s]", clazz.getCanonicalName(), fieldName));
        }

        Field filed = fieldMap.get(fieldName);

        try {
            return filed.get(target);
        } catch (Exception e) {
            String message = String.format("类 [%s] 反射访问属性 [%s] 异常!", clazz.getCanonicalName(), fieldName);
            throw new RuntimeException(message, e);
        }

    }

    /**
     * 直接通过反射设置对象属性的值
     * @param target
     * @param fieldName
     * @param fieldValue
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void setFieldValue(Object target, String fieldName, Object fieldValue){

        if(target == null || isBlank(fieldName)){
            return;
        }
        //map直接返回
        if(target instanceof Map){
            ((Map)target).put(fieldName, fieldValue);
            return;
        }

        Class<?> clazz = null;
        if(target instanceof Class){
            clazz = (Class<?>)target;
        } else {
            clazz = target.getClass();
        }

        Map<String, Field> filedMap = fieldCache.get(clazz);
        //没有属性
        if(filedMap == null || !filedMap.containsKey(fieldName)){

            synchronized (clazz) {
                filedMap = fieldCache.get(clazz);
                if(filedMap == null || !filedMap.containsKey(fieldName)){
                    Field field = findField(clazz, fieldName);
                    if(field != null){
                        field.setAccessible(true);
                        if(filedMap == null){
                            filedMap = new ConcurrentHashMap<String, Field>();//做个长度缓存
                            fieldCache.put(clazz, filedMap);
                        }
                        filedMap.put(fieldName, field);
                    }
                }
            }
        }

        if(filedMap == null || !filedMap.containsKey(fieldName)){
            throw new RuntimeException(String.format("类 [%s] 不存在属性 [%s]", clazz.getCanonicalName(), fieldName));
        }

        Field filed = filedMap.get(fieldName);

        try {
            filed.set(target, fieldValue);
        } catch (Exception e) {
            String message = String.format("类 [%s] 反射访问属性 [%s] 异常!", clazz.getCanonicalName(), fieldName);
            throw new RuntimeException(message, e);
        }

    }

    /**
     * 反射调用类的静态方法(注意本方法不支持重载方法的反射调用)
     *
     * @param clazz
     * @param methodName
     * @param objects
     * @return
     */
    public static Object invokeStaticMethod(Class<?> clazz, String methodName, Object...objects) {
        return invokeMethod(clazz, methodName, objects);
    }

    /**
     * 反射调用方法(注意本方法不支持重载方法的反射调用)
     * @param target
     * @param methodName
     * @param objects
     * @return
     */
    public static Object invokeMethod(Object target, String methodName, Object[] objects) {
        if(target == null || isBlank(methodName)){
            return null;
        }

        Class<?> clazz = null;
        if(target instanceof Class){//本身就是class类 可能是调用静态方法
            clazz = (Class<?>)target;
        } else {
            clazz = target.getClass();
        }

        Map<String, Method> methodMap = methodCache.get(clazz);
        //没有属性
        if(methodMap == null || !methodMap.containsKey(methodName)){
            synchronized (clazz) {
                methodMap = methodCache.get(clazz);
                if(methodMap == null || !methodMap.containsKey(methodName)){
                    Method method = findMethod(clazz, methodName, new Class<?>[]{});
                    if(method != null){
                        method.setAccessible(true);
                        if(methodMap == null){
                            methodMap = new ConcurrentHashMap<String, Method>();//做个长度缓存
                            methodCache.put(clazz, methodMap);
                        }
                        methodMap.put(methodName, method);
                    }
                }
            }

        }

        if(methodMap == null || !methodMap.containsKey(methodName)){
            throw new RuntimeException(String.format("类 [%s] 不存在方法 [%s]", clazz.getCanonicalName(), methodName));
        }

        Method method = methodMap.get(methodName);

        try {
            return method.invoke(target, objects);
        } catch (Exception e) {
            String message = String.format("类 [%s] 反射访问方法 [%s] 异常!", clazz.getCanonicalName(), methodName);
            throw new RuntimeException(message, e);
        }

    }

    public static <A extends Annotation> Field findUniqueFieldWithAnnotation(Class<?> clz, final Class<A> type) {
        final List<Field> fields = new ArrayList<Field>();

        doWithFields(clz, new FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                fields.add(field);
            }
        }, new FieldFilter() {
            @Override
            public boolean matches(Field field) {
                return field.isAnnotationPresent(type);
            }
        });

        if (fields.size() > 1) {
            throw new IllegalStateException("被注释" + type.getSimpleName() + "声明的域不唯一");
        } else if (fields.size() == 1) {
            return fields.get(0);
        }
        return null;

    }

    /**
     * 类似{@link org.springframework.util.ReflectionUtils#doWithFields(Class, FieldCallback, FieldFilter)}
     * 的方法，只是该方法不会递归检查父类上的域
     * @see org.springframework.util.ReflectionUtils#doWithFields(Class, FieldCallback, FieldFilter)
     * @param clazz
     * @param fc
     * @param ff
     * @throws IllegalArgumentException
     */
    public static void doWithDeclaredFields(Class<?> clazz, FieldCallback fc, FieldFilter ff) throws IllegalArgumentException {
        if (clazz == null || clazz == Object.class) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (ff != null && !ff.matches(field)) {
                continue;
            }
            try {
                fc.doWith(field);
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("非法访问属性 '" + field.getName() + "': " + ex);
            }
        }
    }

    /**
     * 获得第一个使用指定注释声明的属性
     * @param clz 属性所在类
     * @param annotationClass 注释类型
     * @return 不存在则返回 null
     */
    public static Field getFirstDeclaredFieldWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获得全部使用指定注释声明的属性
     * @param clz 属性所在类
     * @param annotationClass 注释类型
     * @return 不会返回 null
     */
    public static Field[] getDeclaredFieldsWith(Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        final List<Field> fields = new ArrayList<Field>();
        ReflectionUtils.doWithFields(clazz, new FieldCallback() {

            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (field.isAnnotationPresent(annotationClass)) {
                    fields.add(field);
                }
            }
        });
        return fields.toArray(new Field[0]);
    }

    /**
     * 获得第一个使用指定注释声明的方法
     * @param clz 属性所在类
     * @param annotationClass 注释类型
     * @return 不存在则返回 null
     */
    public static Method getFirstDeclaredMethodWith(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获得全部使用指定注释声明的方法
     *
     * @param clazz  属性所在类
     * @param annotionClass 注释类型
     * @return 不会返回null
     */
    public static Method[] getDeclaredMethodsWith(Class<?> clazz, Class<? extends Annotation> annotionClass) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotionClass)) {
                methods.add(method);
            }
        }
        return methods.toArray(new Method[0]);
    }

    /**
     * 获取全部使用指定注释声明的get方法
     *
     * @param clazz 属性所在类
     * @param annotationClass 注释类型
     * @return
     */
    public static Method[] getDecllaredGetMethodsWith(Class<?> clazz, Class<? extends Annotation> annotationClass){
        List<Method> methods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annotationClass) == null) {
                continue;
            }
            if (method.getReturnType().equals(void.class)) {
                continue;
            }
            if (method.getParameterTypes().length > 0) {
                continue;
            }
            methods.add(method);
        }
        return methods.toArray(new Method[0]);
    }

    /**
     * 获取clazz的超类，但不包括Object.class
     *
     * @param clazz
     * @return
     */
    public static Set<Class<?>> getSupperClass(Class<?> clazz){
        Set<Class<?>> set = new HashSet<>(TypeToken.of(clazz).getTypes().rawTypes());
        set.remove(Object.class);
        return set;
    }

}