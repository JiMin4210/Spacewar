package com.example.final_shooting;

import android.content.Context;

public class Boss extends Enemy{

    public Boss(Context context, int x, int y) {
        super(context,x,y);

        this.nickname = "boss";
        this.speedX = 2;
        this.speedY = 2;
        this.life = 4*DrawFrame.mode; // 첫 보스 생명 5개 속도 5
        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.enemyScore = 15; // 보스 몬스터 죽일시 15점
    }

}