package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Monster extends Enemy{

    public Monster(Context context, int x, int y) {
        super(context,x,y);
        switch((int)(Math.random()*3) + 1){
            case 1:
                this.nickname = "eye";
                this.speedX = DrawFrame.mode; // 슬라임 속도
                this.speedY = DrawFrame.mode; //
                // 슬라임 생명 1개 (부모클래스에서 자동 정의)
                break;
            case 2:
                this.nickname = "zombie1";
                this.speedX = DrawFrame.mode*2; // 날쌘이는 속도 높게
                this.speedY = DrawFrame.mode*2;
                // 날쌘이 생명 1개 (부모클래스에서 자동 정의)
                break;
            case 3:
                this.nickname = "zombie2";
                this.speedX = DrawFrame.mode;
                this.speedY = DrawFrame.mode;
                this.life = this.life * 2; //돼지는 속도가 낮은대신 생명이 2개
                break;
        }

        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.enemyScore = 5; // 일반 몬스터 죽일시 5점
    }

}