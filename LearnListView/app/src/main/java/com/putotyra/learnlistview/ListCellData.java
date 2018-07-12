package com.putotyra.learnlistview;

/**
 * Created by Kojirou on 16/5/6.
 */
public class ListCellData {

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String userName = "Username";
    private String sex = "Woman";
    private int age = 0;

    public ListCellData(String userName, String sex, int age) {
        this.userName = userName;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return getUserName();
    }
}
