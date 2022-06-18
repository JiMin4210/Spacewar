package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Monster extends Enemy{

    public Monster(Context context, int x, int y) {
        super(context,x,y);
        int a = (int)(Math.random()*3) + 1;
        String bitname = "monster" + String.valueOf(a);
        int resID = context.getResources().getIdentifier(bitname , "drawable", context.getPackageName());
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
        this.y = y - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
    }

}