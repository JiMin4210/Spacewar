package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Gun extends Character{

    int subSpeed = 25; // 마우스 클릭 방향으로 총알을 보내려면 증가 값이 일정해야하기 때문에 이 값으로 값을 조절

    public Gun(Context context, int x, int y) {
        super(context,x,y);
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shot);
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (사이즈 계산으로)
        this.y = y - bitsize[1]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (사이즈 계산으로)
    }

    void atan(int clickX, int clickY) // 각도를 찾는 함수
    {
        double angle = Math.atan((double)(y-clickY)/(clickX-x)); // 값이 90 ~ -90까지 표현된다.(라디안이 아님에 유의하자)
        if(clickX-x < 0) // 2사분면, 3사분면
        {
            this.speedX = (int)(-subSpeed*Math.cos(angle));
            this.speedY = (int)(subSpeed*Math.sin(angle));
        }
        else // 1사분면, 4사분면
        {
            this.speedX = (int)(subSpeed*Math.cos(angle));
            this.speedY = (int)(-subSpeed*Math.sin(angle));
        }
    }
    @Override
    public void moveShape(DrawFrame df) {
        outCheck(x + speedX, y+speedY);
        for(int i = 0; i<df.monsters.size(); i++)
            collisionCheck(df.monsters.get(i));
        x += speedX;
        y += speedY;
    }

    @Override
    public void outCheck(int movingX, int movingY)
    {
        if(movingX > DrawFrame.screenWidth - bitsize[0] || movingX < 0 || movingY > DrawFrame.screenHeight  - bitsize[1] || movingY < 0)
        {
            life --;
        }
    }

    @Override
    public void collisionCheck(Character ch) {
        if(x>ch.x-bitsize[0] && x<ch.x+ch.bitsize[0] &&y>ch.y-bitsize[1] && y<ch.y+ch.bitsize[1])
        {
            ch.life --;
            life = 0;
        }
    }

}