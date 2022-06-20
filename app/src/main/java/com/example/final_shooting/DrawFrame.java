package com.example.final_shooting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawFrame extends View {
    Context context;
    static int screenWidth, screenHeight; // 메모리 주소를 고정시킨다.
    static long FPS; // 초당 프레임 몇번인지 계산
    static int buttonbar = 30;//120; // 밑에 버튼바 크기만큼 올려줌 (가상디바이스 = 120, 실제 디바이스 = 30)
    long FRAME;
    Bitmap background;
    Handler handler;
    Hero hero;
    Runnable runnable;
    ArrayList<Monster> monsters; // Array쓰는이유 - 메모리 최적화
    ArrayList<Boss> bosses;
    ArrayList<Gun> guns;
    ArrayList<Item> items;
    ArrayList<Effect> effects;
    Paint scorePaint;
    Bitmap bitmap;


    public DrawFrame(Context context) {
        super(context);
        this.context = context;
        handler = new Handler();
        FRAME = 60;
        FPS = 1000/FRAME; 
        Point displaySize = new Point();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getRealSize(displaySize);
        screenWidth = displaySize.x;
        screenHeight = displaySize.y - buttonbar;
        hero = new Hero(context,screenWidth/2,screenHeight/2); // 히어로는 중심 좌표에서 생성
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.playmap);
        monsters = new ArrayList<>();
        bosses = new ArrayList<>();
        guns = new ArrayList<>();
        items = new ArrayList<>();
        effects = new ArrayList<>();
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(80);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ragemode); // 그냥 실험용
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
        //int test = getJoystick();
        //canvas.drawText(String.valueOf(test),50,50,scorePaint);
        canvas.drawBitmap(hero.bitmap, hero.x, hero.y, null);
        hero.moveShape(this);
        //-------------------------------------------------------------

        //-----------------------몬스터 관련 코딩-------------------------
        if(monsters.size() < 7){
            monsters.add(new Monster(context,(int)(Math.random()*(screenWidth-100)),
                    (int)(Math.random()*(screenHeight-buttonbar))));
        }
        for(int i = 0; i<monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            if(monster.ragemode == 1)
            {
                canvas.drawBitmap(bitmap, monster.x, monster.y, null);
            }
            canvas.drawBitmap(monster.bitmap, monster.x, monster.y, null);
            monster.moveShape(this);
            if(monster.life == 0)
                monsters.remove(monster);

        }
        //-------------------------------------------------------------
        //-----------------------보스 관련 코딩-------------------------
        if((int)(Math.random()*FPS*10) == 1) { // 확률 구현 (약 10초에 한번이 평균)
            if (bosses.size() < 2) {
                bosses.add(new Boss(context, (int) (Math.random() * (screenWidth - 100)),
                        (int) (Math.random() * (screenHeight - buttonbar))));
            }
        }
        for(int i = 0; i<bosses.size(); i++)
        {
            Boss boss = bosses.get(i);
            if(boss.ragemode == 1)
            {
                canvas.drawBitmap(bitmap, boss.x, boss.y, null);
            }
            canvas.drawBitmap(boss.bitmap, boss.x, boss.y, null);
            boss.moveShape(this);
            if(boss.life == 0)
                bosses.remove(boss);

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
        //-----------------------아이템 관련 코딩-------------------------
        if((int)(Math.random()*150) == 1) { // 확률 구현
            if (items.size() < 4) {
                items.add(new Healer(context,(int)(Math.random()*(screenWidth-100)),
                        (int)(Math.random()*(screenHeight-buttonbar))));
            }
        }
        for(int i = 0; i<items.size(); i++)
        {
            Item item = items.get(i);
            canvas.drawBitmap(item.bitmap, item.x, item.y, null);
            item.moveShape(this); // 충돌검사 수행
            if(item.life == 0)
                items.remove(item);
        }
        //-------------------------------------------------------------
        //----------------------이펙트 관련 코딩-------------------------
        for(int i = 0; i<effects.size(); i++)
        {
            Effect effect = effects.get(i);
            effect.checkbitmap();
            canvas.drawBitmap(effect.bitmap, effect.X, effect.Y, null);
            if(++(effect.nownum) > effect.Framenum)
                effects.remove(effect);
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
            hero.x = touchX;
            hero.y = touchY;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            hero.x = touchX;
            hero.y = touchY;
        }

        return true;
    }

}