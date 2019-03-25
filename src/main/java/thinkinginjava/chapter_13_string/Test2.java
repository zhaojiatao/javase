package thinkinginjava.chapter_13_string;

/**
 * @author zhaojiatao
 * @date 2018/9/4
 */
public class Test2 {

    public static void main(String[] args) {

        String str="";
        for (int i=0;i<20;i++){

        }




        StringBuffer stringBuffer=new StringBuffer("start");
        for (int i=0;i<20;i++){
            stringBuffer.append(i);
            stringBuffer.append(",");
        }

        System.out.println(stringBuffer.toString());
        System.out.println(stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length()).toString());



    }


}
