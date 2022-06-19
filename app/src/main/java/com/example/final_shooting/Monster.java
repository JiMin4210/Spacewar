package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Monster extends Enemy{

    public Monster(Context context, int x, int y) {
        super(context,x,y);
        int a = (int)(Math.random()*3) + 1;
        String bitname = "monster" + String.valueOf(a); // 몬스터 이미지는 랜덤으로 생성 + 몬스터마다 점수, 체력 다르게 가능
        int resID = context.getResources().getIdentifier(bitname , "drawable", context.getPackageName());
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x;
        this.y = y;
        this.enemyScore = 5; // 일반 몬스터 죽일시 5점
    }

}