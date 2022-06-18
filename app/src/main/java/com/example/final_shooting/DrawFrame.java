package com.example.final_shooting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawFrame extends View {
    Context context;
    Hero hero;
    int screenWidth, screenHeight;
    Bitmap background;
    Handler handler;
    long FRAME;
    Runnable runnable;
    ArrayList<Monster> monsters;

    public DrawFrame(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();
        FRAME = 100;
        Point displaySize = new Point();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getRealSize(displaySize);
        screenWidth = displaySize.x;
        screenHeight = displaySize.y;
        hero = new Hero(context,screenWidth/2,screenHeight/2); // 히어로는 중심 좌표에서 생성
        Toast.makeText(context, String.valueOf(screenHeight), Toast.LENGTH_SHORT).show();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        monsters = new ArrayList<>();

        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(hero.bitmap, hero.x, hero.y, null);
        int a = (int)(Math.random()*2);
        if(a == 1) {
            hero.x += hero.speedX;
            hero.y += hero.speedY;
        }
        else{
            hero.x -= hero.speedX;
            hero.y -= hero.speedY;
        }
        if(monsters.size() < 4){
            monsters.add(new Monster(context,(int)(Math.random()*1000)+100,(int)(Math.random()*1500)));
        }
        for(int i = 0; i<monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            canvas.drawBitmap(monster.bitmap, monster.x, monster.y, null);
        }
        handler.postDelayed(runnable,FRAME);
    }

}