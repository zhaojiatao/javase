package listCopy;

import java.io.Serializable;

/**
 * @author zhaojiatao
 * @date 2019/3/12
 */
public class Address implements Cloneable,Serializable {

    private String address;



    public Address(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    @Override
    protected Object clone() {
        Address address = null;
        try {
            address = (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return address;
    }



}
