package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Enemy extends Character{
    int enemyScore; // 적들을 죽일시 얻는 점수
    long lifeTime; // 적들이 생존한 시간
    int angryTime = 5; // 10초가 지나면 화나서? 주인공 쫒아오게함
    int angrySpeed = 1; // 1초마다 추적시간을 점점 증가시키기 위함.
    int xymargin = 300; // 추적 좌표에서 얼마만큼 오차가 나도 되는지
    int traceX, traceY;
    int bufX, bufY;

    public Enemy(Context context, int x, int y) {
        super(context,x,y);
        this.lifeTime = 0;
        this.speedX = (int)(Math.random()*5)+3; // 2~7구간 랜덤
        this.speedY = (int)(Math.random()*5)+3;
        this.traceX = (int)(Math.random()*(DrawFrame.screenWidth-100)); // 처음에 추적할 위치
        this.traceY = (int)(Math.random()*(DrawFrame.screenHeight-DrawFrame.buttonbar));
    }

    @Override
    public void moveShape(DrawFrame df) {
        outCheck(x + speedX, y+speedY);
        lifeTime ++;
        collisionCheck(df.hero);
        if(lifeTime <= df.FPS*angryTime) {
            traceXY(traceX, traceY, 0, 0, 0);
            if(x>traceX-bitsize[0] && x<traceX+xymargin &&y>traceY-bitsize[1] && y<traceY+xymargin)
            {
                this.traceX = (int)(Math.random()*(DrawFrame.screenWidth-100)); // 새로운 위치 추적
                this.traceY = (int)(Math.random()*(DrawFrame.screenHeight-DrawFrame.buttonbar));
            }
        }
        else// n초 이상 시 주인공을 추적하는 if문
            traceXY(df.hero.x,df.hero.y,df.hero.bitsize[0], df.hero.bitsize[1], 1);
        x += speedX; // ->이걸 8방향으로 랜덤하게 움직이고 방향있게 해주기
        y += speedY;

        speedX = bufX;
        speedY = bufY;
    }

    @Override
    public void outCheck(int movingX, int movingY)
    {
        if(movingX > DrawFrame.screenWidth - bitsize[0])
        {
            speedX = -Math.abs(speedX);
        }
        else if(movingX < 0)
        {
            speedX = Math.abs(speedX);
        }
        if(movingY > DrawFrame.screenHeight  - bitsize[1])
        {
            speedY = -Math.abs(speedY);
        }
        else if(movingY < 0)
        {
            speedY = Math.abs(speedY);
        }
    }

    @Override
    public void collisionCheck(Character ch) {
        if(x>ch.x-bitsize[0] && x<ch.x+ch.bitsize[0] &&y>ch.y-bitsize[1] && y<ch.y+ch.bitsize[1])
        {
            ch.life --;
            life = 0; // 주인공과 부딪히는 적군들은 모두 없어진다 - 점수는 안오름
        }
    }

    public void traceXY(int tx, int ty,int marginX,int marginY, int flag) // 대상을 추적하는 함수
    {
        if(x > tx + marginX)
            speedX = -Math.abs(speedX);

        else if(x + bitsize[0] < tx)
            speedX = Math.abs(speedX);

        if(y > ty + marginY)
            speedY = -Math.abs(speedY);

        else if(y + bitsize[1] < ty)
            speedY = Math.abs(speedY);

        bufX = speedX;
        bufY = speedY;

        if(x < tx + marginX && x + bitsize[0] > tx) // 지그제그 움직임 오류 해결 (조금이라도 걸치면 직진)
            speedX = 0;
        else if(y < ty + marginY && y + bitsize[1] > ty)
            speedY = 0;

        if(flag == 1 && lifeTime % (DrawFrame.FPS * angrySpeed) == 0) // 추적 시작후 n초마다 속도가 1씩 빨라진다.
        {
            if(bufX < 0) // 지그제그 오류 해결용도 = buf
                bufX--;
            else
                bufX++;

            if(bufY < 0)
                bufY--;
            else
                bufY++;

        }
    }



}