package com.example.final_shooting;

import android.content.Context;
import android.graphics.BitmapFactory;

public class Enemy extends Character{
    int enemyScore; // 적들을 죽일시 얻는 점수
    int lifeTime = 0; // 적들이 생존한 시간 - 5초가 지나면 화나서? 주인공 쫒아오게함
    int mainCx; // 적들은 주인공의 좌표값을 알고있어야 추적 할 수 있기떄문에
    int mainCy; // 적들에게만 주인공의 좌표값을 넣어줌

    public Enemy(Context context, int x, int y) {
        super(context,x,y);
    }

}