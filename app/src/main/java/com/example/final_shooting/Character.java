package com.example.final_shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Character {
    Context context;
    Bitmap bitmap;
    int bitsize[] = new int[2]; // 0번 = Width, 1번 = Height
    int x, y;
    int speedX, speedY;
    int life;
    int direction; // 바라보는 방향을 넣을것이다.

    public Character(Context context, int x, int y) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.life = 1;
        this.direction = 0;
    }

    public abstract void moveShape(DrawFrame df);
    public abstract void outCheck(int movingX, int movingY);
    public abstract void collisionCheck(Character ch); // 충돌 검사 - 모든 객체가 해야하기에 공통
}