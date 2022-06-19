package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Enemy extends Character{
    int enemyScore; // 적들을 죽일시 얻는 점수
    int lifeTime; // 적들이 생존한 시간 - 5초가 지나면 화나서? 주인공 쫒아오게함
    int mainCx; // 적들은 주인공의 좌표값을 알고있어야 추적 할 수 있기떄문에
    int mainCy; // 적들에게만 주인공의 좌표값을 넣어줌

    public Enemy(Context context, int x, int y) {
        super(context,x,y);
        this.lifeTime = 0;
        this.speedX = (int)(Math.random()*5)+20; // 2~7구간 랜덤
        this.speedY = (int)(Math.random()*5)+20;
    }

    @Override
    public void moveShape(DrawFrame df) {
        outCheck(x + speedX, y+speedY);
        //lifeTime ++; // moveShape는 0.1초에 한번 실행되니까 lifeTime이 10이면 1초가 살아있는것.
        collisionCheck(df.hero);
        //if(lifeTime >= 50) // 5초 이상 시 주인공을 추적하는 if문
            //traceCharacter(x,y);
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

}