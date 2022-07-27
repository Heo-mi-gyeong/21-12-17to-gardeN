package com.cookandroid.capston;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class itemView2 extends LinearLayout{
    TextView title,content;
    ImageView garden1;

    public itemView2(Context context){
        super(context);
        init(context);
    }
    public itemView2(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview2,this,true);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        garden1=findViewById(R.id.garden1);

    }
    public void setName(String name){
        title.setText(name);
    }
    public void setMobile(String mobile){
        content.setText(mobile);
    }
    public void setImage(int resld){
        garden1.setImageResource(resld);
    }
}
