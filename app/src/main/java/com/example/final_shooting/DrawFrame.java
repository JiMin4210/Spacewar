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
    ArrayList<Monster> monsters; // Array쓰는이유 - 메모리 최적화
    ArrayList<Gun> guns;

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
        guns = new ArrayList<>();
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
            monster.moveShape(this);
            if(monster.life == 0)
                monsters.remove(monster);
        }
        //-------------------------------------------------------------
        //-----------------------총알 관련 코딩--------------------------
        for(int i = 0; i<guns.size(); i++)
        {
            Gun gun = guns.get(i);
            canvas.drawBitmap(gun.bitmap, gun.x, gun.y, null);
            gun.moveShape(this);
            if(gun.life == 0)
                guns.remove(gun);
        }
        //-------------------------------------------------------------
        handler.postDelayed(runnable,FRAME);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_UP){

        }
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(guns.size() < 5)
            {
                Gun gun = new Gun(context,hero.x + hero.bitsize[0]/2,hero.y + hero.bitsize[1]/2);
                gun.atan(touchX,touchY); // 총알 좌표 계산
                guns.add(gun);
            }
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){

        }

        return true;
    }
}