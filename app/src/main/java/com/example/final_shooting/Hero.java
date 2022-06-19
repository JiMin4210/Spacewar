package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Hero extends Character{
    ArrayList<Gun> guns; // 총알 객체를 5개 선언함으로써 총알이 5개로 제한된다.

    public Hero(Context context, int x, int y) {
        super(context,x,y);
        this.speedX = 15;
        this.speedY = 15;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
        this.y = y - bitsize[1]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
    }

}