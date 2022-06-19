package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Enemy extends Character{
    int enemyScore; // 적들을 죽일시 얻는 점수
    long lifeTime; // 적들이 생존한 시간
    int angryTime = 5; // 5초가 지나면 화나서? 주인공 쫒아오게함
    int angrySpeed = 3; // 3초마다 추적시간을 점점 증가시키기 위함.

    public Enemy(Context context, int x, int y) {
        super(context,x,y);
        this.lifeTime = 0;
        this.speedX = (int)(Math.random()*5)+20; // 2~7구간 랜덤
        this.speedY = (int)(Math.random()*5)+20;
    }

    @Override
    public void moveShape(DrawFrame df) {
        outCheck(x + speedX, y+speedY);
        lifeTime ++;
        collisionCheck(df.hero);
        if(lifeTime >= df.FPS*angryTime) // n초 이상 시 주인공을 추적하는 if문
            traceCharacter(df.hero);
        x += speedX; // ->이걸 8방향으로 랜덤하게 움직이고 방향있게 해주기
        y += speedY;
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

    public void traceCharacter(Hero hero) // 주인공을 추적하는 함수
    {
        if(x > hero.x + hero.bitsize[0])
        {
            speedX = -Math.abs(speedX);
            if(lifeTime % DrawFrame.FPS * angrySpeed == 0) // 추적 시작후 n초마다 속도가 1씩 빨라진다.
                speedX --;
        }
        else if(x + bitsize[0] < hero.x)
        {
            speedX = Math.abs(speedX);
            if(lifeTime % DrawFrame.FPS * angrySpeed == 0)
                speedX ++;
        }
        if(y > hero.y + hero.bitsize[1])
        {
            speedY = -Math.abs(speedY);
            if(lifeTime % DrawFrame.FPS * angrySpeed == 0)
                speedY --;
        }
        else if(y + bitsize[1] < hero.y)
        {
            speedY = Math.abs(speedY);
            if(lifeTime % DrawFrame.FPS * angrySpeed == 0)
                speedY ++;
        }
    }

}