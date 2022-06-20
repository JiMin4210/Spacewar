package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Monster extends Enemy{

    public Monster(Context context, int x, int y) {
        super(context,x,y);
        switch((int)(Math.random()*3) + 1){
            case 1:
                this.nickname = "eye";
                break;
            case 2:
                this.nickname = "zombie1";
                break;
            case 3:
                this.nickname = "zombie2";
                break;
        }

        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.enemyScore = 5; // 일반 몬스터 죽일시 5점
    }

}