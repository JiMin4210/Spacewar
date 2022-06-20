package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Hero extends Character{

    public Hero(Context context, int x, int y) {
        super(context,x,y);
        this.speedX = 7;
        this.speedY = 7;
        this.direction = 7; // 기본 방향은 아랫방향 - 주인공 기준
        checkbitmap();
        bitsize[0] = bitmap.getWidth();
        bitsize[1] = bitmap.getHeight();
        this.x = x - bitsize[0]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
        this.y = y - bitsize[1]/2; // 원하는 좌표가 중앙이 되도록 조절해줌 (화면 사이즈 계산으로)
    }

    @Override
    public void moveShape(DrawFrame df) {
        joystickCheck();
        outCheck(x, y);
        checkbitmap();
    }

    @Override
    public void outCheck(int movingX, int movingY) {
        if(movingX > DrawFrame.screenWidth - bitsize[0])
        {
            x = DrawFrame.screenWidth - bitsize[0];
        }
        else if(movingX < 0)
        {
            x = 0;
        }
        if(movingY > DrawFrame.screenHeight  - bitsize[1])
        {
            y = DrawFrame.screenHeight  - bitsize[1];
        }
        else if(movingY < 0)
        {
            y = 0;
        }
    }

    @Override
    public void collisionCheck(Character ch) {}

    public void joystickCheck() {
        int decode = getJoystick();
        if (decode % 10000 == 1) //오
        {
            x = x + speedX;
            direction = 1;
        } else if (decode % 10000 == 10) // 대각 위오
        {
            x = x + speedX;
            y = y - speedY;
            direction = 2;
        } else if (decode % 10000 == 11) // 위
        {
            y = y - speedY;
            direction = 3;
        } else if (decode % 10000 == 100) // 대각 위왼
        {
            x = x - speedX;
            y = y - speedY;
            direction = 4;
        } else if (decode % 10000 == 101) // 왼
        {
            x = x - speedX;
            direction = 5;
        } else if (decode % 10000 == 110) // 대각 아래왼
        {
            x = x - speedX;
            y = y + speedY;
            direction = 6;
        } else if (decode % 10000 == 111) // 아래
        {
            y = y + speedX;
            direction = 7;
        } else if (decode % 10000 == 1000) // 대각 아래 오른
        {
            x = x + speedX;
            y = y + speedY;
            direction = 8;
        }
    }
    public void checkbitmap()
    {
        String bitname = "hero" + String.valueOf(direction); // 몬스터 이미지는 랜덤으로 생성 + 몬스터마다 점수, 체력 다르게 가능
        int resID = context.getResources().getIdentifier(bitname , "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
    }
    public native int getJoystick();

}