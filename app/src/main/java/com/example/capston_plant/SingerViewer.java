package com.example.capston_plant;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class SingerViewer extends LinearLayout {

    ImageView imageView;
    public SingerViewer(Context context) {
        super(context);

        init(context);
    }


    public SingerViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.simple_image,this,true);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setItem(SingerItem singerItem) {
        imageView.setImageResource(singerItem.getImage());
    }
}
