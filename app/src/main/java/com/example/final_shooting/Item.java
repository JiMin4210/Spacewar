package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Item extends Character{

    int whatItem;

    public Item(Context context, int x, int y) {
        super(context,x,y);
        switch((int)(Math.random()*3) + 1){
            case 1:
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heal);
                bitsize[0] = bitmap.getWidth();
                bitsize[1] = bitmap.getHeight();
                this.whatItem = 1; // 힐팩
                break;
            case 2: 
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.strong);
                bitsize[0] = bitmap.getWidth();
                bitsize[1] = bitmap.getHeight();
                this.whatItem = 2; // 총알 강해짐
                break;
            case 3:
                this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.speed);
                bitsize[0] = bitmap.getWidth();
                bitsize[1] = bitmap.getHeight();
                this.whatItem = 3;
                break;
        }
    }

    @Override
    public void moveShape(DrawFrame df) {
        collisionCheck(df.hero, df);
    }

    @Override
    public void collisionCheck(Character ch, DrawFrame df) {
        if(x>ch.x-bitsize[0] && x<ch.x+ch.bitsize[0] &&y>ch.y-bitsize[1] && y<ch.y+ch.bitsize[1])
        {
            if(whatItem == 1)
            {
                ch.life ++;
                if(ch.life >8)
                    ch.life = 8;

                ReciveLedValue(ch.life);
            }
            else if(whatItem == 2){
                ch.power++;
            }
            else if(whatItem == 3){
                ch.speedX++;
                ch.speedY++;
            }


            if(df.hero.face != 1) {
                df.hero.face = 1;
                df.hero.facetime = 0;
                ReceiveDotValue(df.hero.face);

            }
            life = 0; // 주인공과 부딪히는 아이템은 없어져야하기 때문
        }
    }

    @Override
    public void outCheck(int movingX, int movingY){}

}