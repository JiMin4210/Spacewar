package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Healer extends Item{

    public Healer(Context context, int x, int y) {
        super(context,x,y);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heal);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.whatItem = 1;
    }

}