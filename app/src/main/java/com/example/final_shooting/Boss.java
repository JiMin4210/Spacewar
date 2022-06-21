package com.example.final_shooting;

import android.content.Context;

public class Boss extends Enemy{

    public Boss(Context context, int x, int y) {
        super(context,x,y);
        switch((int)(Math.random()*2) + 1){
            case 1:
                this.nickname = "boss";
                this.speedX = 3;
                this.speedY = 3;
                this.life = 5; // 첫 보스 생명 5개 속도 5
                break;
            case 2:
                this.nickname = "boss";
                this.speedX = 5;
                this.speedY = 5;
                this.life = 5; // 눈깔보스 생명 5개 속도 5
                break;
        }
        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.enemyScore = 15; // 보스 몬스터 죽일시 15점
    }

}