package com.example.final_shooting;

import android.content.Context;

public class Item extends Character{

    int whatItem;

    public Item(Context context, int x, int y) {
        super(context,x,y);
    }

    @Override
    public void moveShape(DrawFrame df) {
        collisionCheck(df.hero);
    }

    @Override
    public void collisionCheck(Character ch) {
        if(x>ch.x-bitsize[0] && x<ch.x+ch.bitsize[0] &&y>ch.y-bitsize[1] && y<ch.y+ch.bitsize[1])
        {
            if(whatItem == 1)
                ch.life ++;
            else if(whatItem == 2){
                ch.life++;
            }
            life = 0; // 주인공과 부딪히는 아이템은 없어져야하기 때문
        }
    }

    @Override
    public void outCheck(int movingX, int movingY){}

}