package com.cookandroid.capston;

public class item2 {
    String name;
    String mobile;
    int resld;

    public item2(String name,String mobile,int resld){
        this.name=name;
        this.mobile=mobile;
        this.resld=resld;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getMobile(){
        return mobile;
    }
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    public int getResld(){
        return resld;
    }
    @Override
    public String toString(){
        return  "item{" + "name'" +name+'\''+",moblie='"+mobile+'\''+'}';
    }

}