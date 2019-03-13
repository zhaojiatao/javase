package listCopy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaojiatao
 * @date 2019/3/12
 */
public class MyClass {

    public static void main(String[] args) {
        List<Address> addressList = new ArrayList<>();
        addressList.add(new Address("北京市"));
        Student student = new Student("李晓东");
        student.addressList = addressList;

        Student student2 = (Student) student.clone();
        student2.addressList = depCopy2(addressList);

        println(student.getName() + "---" + student.addressList.get(0).getAddress());
        println(student2.getName() + "---" + student2.addressList.get(0).getAddress());
        println("改变student2的姓名后--------------");
        student2.setName("张天");
        student2.addressList.get(0).setAddress("湖南省");
        println(student.getName() + "---" + student.addressList.get(0).getAddress());
        println(student2.getName() + "---" + student2.addressList.get(0).getAddress());
    }

    public static void println(String str) {
        System.out.println(str);
    }

    /***
     * 方法二
     * 需要Address实现cloneable接口和重写clone方法，次方法有限制性，
     * 例如要先声明List是保存的什么对象，并且当碰到对象里面还持有List集合的时候
     * 就不管用的，所以建议使用第一种方法
     *
     * @param addresses
     * @return
     */
    public static List<Address> depCopy2(List<Address> addresses) {
        List<Address> destList = new ArrayList<>();
        for (Address address : addresses) {
            destList.add((Address) address.clone());
        }
        return destList;
    }

    /***
     * 方法一对集合进行深拷贝 注意需要对泛型类进行序列化(实现Serializable)
     *
     * @param srcList
     * @param <T>
     * @return
     */
    public static <T> List<T> depCopy(List<T> srcList) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(srcList);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream inStream = new ObjectInputStream(byteIn);
            List<T> destList = (List<T>) inStream.readObject();
            return destList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}