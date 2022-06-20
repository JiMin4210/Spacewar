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
    int direction; // 바라보는 방향을 넣을것이다. 1 = 오른쪽부터 위로 한바퀴

    public Character(Context context, int x, int y) {
        this.context = context;
        this.x = x;
        this.y = y;
        this.life = 1;
    }

    public abstract void moveShape(DrawFrame df);
    public abstract void outCheck(int movingX, int movingY);
    public abstract void collisionCheck(Character ch, DrawFrame df); // 충돌 검사 - 모든 객체가 해야하기에 공통
                                                                     // 이펙트를 위해 df까지 써줌
}