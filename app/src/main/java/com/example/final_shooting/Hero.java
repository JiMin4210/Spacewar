package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Hero extends Character{

    public Hero(Context context, int x, int y) {
        super(context,x,y);
        this.speedX = 7;
        this.speedY = 7;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero7);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
        this.y = y - bitsize[1]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
    }

    @Override
    public void moveShape(DrawFrame df) {

    }

    @Override
    public void outCheck(int movingX, int movingY) {
    }

    @Override
    public void collisionCheck(Character ch) {}

}