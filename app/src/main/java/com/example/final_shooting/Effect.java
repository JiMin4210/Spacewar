package com.example.final_shooting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Effect {

    Bitmap bitmap;
    int Framenum;
    int nownum;
    int X, Y;
    String name;
    Context context;

    public Effect(Context context, int X, int Y, String name, int Framenum) {
        this.context = context;
        this.Framenum = Framenum;
        this.name = name;
        this.nownum = 1;
        this.X = X;
        this.Y = Y;
    }

    public void checkbitmap()
    {
        String bitname = name + String.valueOf(nownum); // 몬스터 이미지는 랜덤으로 생성 + 몬스터마다 점수, 체력 다르게 가능
        int resID = context.getResources().getIdentifier(bitname , "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
    }
}