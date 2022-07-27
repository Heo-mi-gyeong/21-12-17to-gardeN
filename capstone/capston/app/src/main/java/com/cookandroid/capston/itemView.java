package com.cookandroid.capston;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class itemView extends LinearLayout{
    TextView title,getplant;
    ImageView garden1;

    public itemView(Context context){
        super(context);
        init(context);
    }
    public itemView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview,this,true);
        title=findViewById(R.id.title);
        getplant=findViewById(R.id.getplant);
        garden1=findViewById(R.id.garden1);

    }
    public void setName(String name){
        title.setText(name);
    }
    public void setMobile(String mobile){
        getplant.setText(mobile);
    }
    public void setImage(int resld){
        garden1.setImageResource(resld);
    }
}
