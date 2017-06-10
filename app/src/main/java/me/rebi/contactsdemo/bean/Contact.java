package me.rebi.contactsdemo.bean;

/**
 * Created by rebi-mac on 2016/11/24.
 */

public class Contact {
    private String _id;
    private String PinYin;
    private String name;
    private String phoneNum;

    public Contact(String _id, String pinYin, String name, String phoneNum) {
        this._id = _id;
        this.PinYin = pinYin;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "_id='" + _id + '\'' +
                ", PinYin='" + PinYin + '\'' +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
