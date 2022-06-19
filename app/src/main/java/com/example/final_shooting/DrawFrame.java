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
    static int screenWidth, screenHeight; // 메모리 주소를 고정시킨다.
    final int buttonbar = 120; // 밑에 버튼바 크기만큼 올려줌
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
        screenHeight = displaySize.y - buttonbar;
        hero = new Hero(context,screenWidth/2,screenHeight/2); // 히어로는 중심 좌표에서 생성
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
        //-----------------------주인공 관련 코딩-------------------------
        canvas.drawBitmap(hero.bitmap, hero.x, hero.y, null);
        //-------------------------------------------------------------

        //-----------------------몬스터 관련 코딩-------------------------
        if(monsters.size() < 4){
            monsters.add(new Monster(context,(int)(Math.random()*(screenWidth-100)),
                    (int)(Math.random()*(screenHeight-buttonbar))));
        }
        for(int i = 0; i<monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            canvas.drawBitmap(monster.bitmap, monster.x, monster.y, null);
            monster.moveShape();
        }
        //-------------------------------------------------------------
        handler.postDelayed(runnable,FRAME);
    }

}