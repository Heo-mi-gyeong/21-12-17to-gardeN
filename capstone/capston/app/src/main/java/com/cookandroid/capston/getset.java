package com.cookandroid.capston;

import android.app.Application;
import android.content.Intent;
import android.widget.ImageView;

public class getset extends Application {
    private int count;
    private String userid;
    private int level;
    private String Id;

    public int getCount() {

        return count;
    }
    public void setCount( int count )
    {
        this.count = count;
    }
    public String getId() {

        return Id;
    }
    public void setId( String Id) {

        this.Id= Id;
    }
    public String getUserId() {
        return userid;
    }
    public void setUserId(String userid) {
        this.userid = userid;
    }

    public int getImage(){
        return level;
    }
    public void setImage(int level) {
        this.level = level;
    }
}
