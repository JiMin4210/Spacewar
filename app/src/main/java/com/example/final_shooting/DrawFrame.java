package com.example.final_shooting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DrawFrame extends View {
    Context context;
    static int screenWidth, screenHeight; // 메모리 주소를 고정시킨다.
    static long FPS; // 초당 프레임 몇번인지 계산
    static int buttonbar = 30;//120; // 밑에 버튼바 크기만큼 올려줌 (가상디바이스 = 120, 실제 디바이스 = 30)
    static int mode;
    static int shotname;
    long FRAME;
    Bitmap background;
    Handler handler;
    Hero hero;
    Runnable runnable;
    Runnable runmotor; // 모터 전용 러너블객체
    ArrayList<Monster> monsters; // Array쓰는이유 - 메모리 최적화
    ArrayList<Boss> bosses;
    ArrayList<Gun> guns;
    ArrayList<Item> items;
    ArrayList<Effect> effects;
    int score;
    Paint scorePaint;
    int overFlag = 1; // 이걸 해줘야 종료화면이 두번 안뜸 + 스레드 더이상 안 만듬 = 에러가 안남
    int gunflag = 0;
    int gametime = 0;
    int killm = 0; // 몬스터 죽인 회수

    public DrawFrame(Context context, int mode, int shotname) {
        super(context);
        this.context = context;
        this.mode = mode;
        this.shotname = shotname;
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
        hero.ReceiveDotValue(hero.face); // 처음 시작할 땐 웃는표정
        //ReceiveTextLcdValue("hidwadwa", "abcdwadwa");
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        runmotor = new Runnable() {
            @Override
            public void run() {
                SetMotorState(0,0,10); // 좌우방향 랜덤하게
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        //-----------------------생명이 끝나면---------------------------
        //canvas.drawText(String.valueOf(score),100,200,scorePaint);
        //canvas.drawText(String.valueOf(hero.life),100,500,scorePaint);
        if(hero.life <= 0 && overFlag == 1) { // overFlag 안해주면 gameover 엑티비티가 2번실행되는 문제점이있기에 방지해줌
            overFlag = 0;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            intent.putExtra("shotname", shotname);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        //-----------------------주인공 관련 코딩-------------------------
        //int test = hero.getJoystick();
        //canvas.drawText(String.valueOf(test),300,300,scorePaint);
        canvas.drawBitmap(hero.bitmap, hero.x, hero.y, null);
        hero.moveShape(this);
        //-------------------------------------------------------------
        //-----------------------아이템 관련 코딩-------------------------
        if((int)(Math.random()*150) == 1) { // 확률 구현
            if (items.size() < 4) {
                items.add(new Item(context,(int)(Math.random()*(screenWidth-100)),
                        (int)(Math.random()*(screenHeight-buttonbar))));
            }
        }
        for(int i = 0; i<items.size(); i++)
        {
            Item item = items.get(i);
            canvas.drawBitmap(item.bitmap, item.x, item.y, null);
            item.moveShape(this); // 충돌검사 수행
            if(item.life <= 0)
                items.remove(item);
        }
        //-------------------------------------------------------------
        //-----------------------몬스터 관련 코딩-------------------------
        if((int)(Math.random()*FPS) == 1) { // 1초에 한번씩 몬스터 나옴
            if (monsters.size() < 6) {
                monsters.add(new Monster(context, (int) (Math.random() * (screenWidth - 100)),
                        (int) (Math.random() * (screenHeight - buttonbar))));
            }
        }
        for(int i = 0; i<monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            canvas.drawBitmap(monster.bitmap, monster.x, monster.y, null);
            if(monster.ragemode == 1){
                if(monster.direction == 1) // 방향에 따라 ragebitmap도 다르게 해준다.
                    canvas.drawBitmap(monster.ragebitmap, monster.x, monster.y, null);
                else
                    canvas.drawBitmap(monster.ragebitmap, monster.x+monster.bitsize[0]-monster.ragebitmap.getWidth(), monster.y, null);
            }
            monster.moveShape(this);
            if(monster.life <= 0)
                monsters.remove(monster);

        }
        //-------------------------------------------------------------
        //-----------------------보스 관련 코딩-------------------------
        if((int)(Math.random()*FPS*10) == 1) { // 확률 구현 (약 10초에 한번이 평균)
            if (bosses.size() < 2) {
                bosses.add(new Boss(context, (int)(Math.random() * (screenWidth - 100)),
                        (int) (Math.random() * (screenHeight - buttonbar))));
                if(hero.face != 3) { // 놀란 표정으로 전환
                    hero.face = 3;
                    hero.facetime = 0;
                    hero.ReceiveDotValue(hero.face);
                }
                SetMotorState(1,(int)(Math.random()*2),10); // 좌우방향 랜덤하게
                handler.postDelayed(runmotor,720);
            }
        }
        for(int i = 0; i<bosses.size(); i++)
        {
            Boss boss = bosses.get(i);
            canvas.drawBitmap(boss.bitmap, boss.x, boss.y, null);
            if(boss.ragemode == 1)
            {
                if(boss.direction == 1)
                    canvas.drawBitmap(boss.ragebitmap, boss.x, boss.y, null);
                else
                    canvas.drawBitmap(boss.ragebitmap, boss.x+boss.bitsize[0]-boss.ragebitmap.getWidth(), boss.y, null);
            }
            boss.moveShape(this);
            if(boss.life <= 0)
                bosses.remove(boss);

        }
        //----------------------총관련 스위치 코드-------------------------
        if(gametime%(2) == 0) { //채터링 준 것
            int value;
            value = DeviceOpen();

            if (value != -1)
                value = ReceivePushSwitchValue();
            if (value != -1)
                DeviceClose();
            if (value == 16) // 총알 발사
                gundirection();
            else if (value == 32 && hero.boom == 1)
            {
                hero.boom = 0;
                for(int i = 0; i<bosses.size(); i++)
                {
                    Boss boss = bosses.get(i);
                    effects.add(new Effect(context,boss.x-15,boss.y-boss.bitsize[1]-10,"explosion",9));
                    boss.life = 0;
                    //bosses.remove(boss);
                }
                for(int i = 0; i<monsters.size(); i++)
                {
                    Monster monster = monsters.get(i);
                    effects.add(new Effect(context,monster.x-15,monster.y-monster.bitsize[1]-10,"explosion",9));
                    monster.life = 0;
                    //monsters.remove(monster);

                }
            }
        }
        //-------------------------------------------------------------
        if(gunflag == 1) // 총소리 관련 코드
            ReceiveBuzzerValue(gunflag++);
        else if (gunflag >= 2)
        {
            gunflag = 0;
            ReceiveBuzzerValue(gunflag);
        }
        //-----------------------총알 관련 코딩--------------------------
        for(int i = 0; i<guns.size(); i++)
        {
            Gun gun = guns.get(i);
            canvas.drawBitmap(gun.bitmap, gun.x, gun.y, null);
            gun.moveShape(this);
            if(gun.life <= 0)
                guns.remove(gun);
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

        //---------------------lcd관련 코드------------------------------

        if(gametime%(FPS*1) == 0)
        {
            String f1 = " T : "+String.valueOf(gametime/FPS)+"  K:" + String.valueOf(killm);
            String f2 = " S : "+String.valueOf(hero.speedX)+"  P:" + String.valueOf(hero.power);
            ReceiveTextLcdValue(f1, f2);
        }

        //---------------프레임마다 실행하기 위한 코드---------------------
        if(overFlag == 1) {
            handler.postDelayed(runnable, FRAME);
            gametime++;
        }
        else {
            hero.face = 2;
            hero.ReceiveDotValue(hero.face);
        }
        //-------------------------------------------------------------

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
                gunflag = 1;
                Gun gun = new Gun(context,hero.x + hero.bitsize[0]/2,hero.y + hero.bitsize[1]/2);
                gun.atan(touchX,touchY); // 총알 좌표 계산
                guns.add(gun);
            }
            //hero.x = touchX;
            //hero.y = touchY;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            //hero.x = touchX;
            //hero.y = touchY;
        }

        return true;
    }
    void setfndscore()
    {
        ReceiveFndValue(String.valueOf(score));
    }

    void gundirection()
    {
        if(guns.size() < 4/mode) // 최대 날릴수있는 총알 개수
        {
            gunflag = 1;
            Gun gun = new Gun(context,hero.x + hero.bitsize[0]/2,hero.y + hero.bitsize[1]/2);
            switch (hero.direction){
                case 1:
                    gun.speedX = gun.subSpeed;
                    gun.speedY = 0;
                    break;
                case 2:
                    gun.speedX = gun.subSpeed;
                    gun.speedY = -gun.subSpeed;
                    break;
                case 3:
                    gun.speedX = 0;
                    gun.speedY = -gun.subSpeed;
                    break;
                case 4:
                    gun.speedX = -gun.subSpeed;
                    gun.speedY = -gun.subSpeed;
                    break;
                case 5:
                    gun.speedX = -gun.subSpeed;
                    gun.speedY = 0;
                    break;
                case 6:
                    gun.speedX = -gun.subSpeed;
                    gun.speedY = gun.subSpeed;
                    break;
                case 7:
                    gun.speedX = 0;
                    gun.speedY = gun.subSpeed;
                    break;
                case 8:
                    gun.speedX = gun.subSpeed;
                    gun.speedY = gun.subSpeed;
                    break;
            }
            guns.add(gun);
        }
    }

    public native int ReceiveFndValue(String a);
    public native int ReceiveBuzzerValue(int x);
    public native int ReceiveTextLcdValue(String ptr1, String ptr2);
    public native int DeviceOpen(); // 이건 핸들러로 하기에 엑티비티 다시보기
    public native int DeviceClose();
    public native int ReceivePushSwitchValue();
    public native String SetMotorState(int act, int dir, int spd);
}