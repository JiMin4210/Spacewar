package com.example.final_shooting;

import android.content.Context;

public class Boss extends Enemy{

    public Boss(Context context, int x, int y) {
        super(context,x,y);
        switch((int)(Math.random()*2) + 1){
            case 1:
                this.nickname = "boss";
                break;
            case 2:
                this.nickname = "boss";
                break;
        }
        this.life = 5;
        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.enemyScore = 15; // 보스 몬스터 죽일시 15점
    }

}