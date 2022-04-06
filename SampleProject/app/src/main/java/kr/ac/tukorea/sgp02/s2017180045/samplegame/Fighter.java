package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Fighter implements GameObject{

    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    private float x,y;
    private float dx,dy;
    private float tx,ty;

    public Fighter(){
        dstRect.set(0,0,200,200);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update()
    {
        float angle =(float)Math.atan2((ty-y),(tx-x));
        float speed = 1000;
        float dist = speed * MainGame.getInstance().frameTime;
        dx= (float) (dist * Math.cos(angle));
        dy= (float) (dist * Math.sin(angle));
        x+=dx;
        y+=dy;
        dstRect.offset(dx,dy);


    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setTargetPosition(int x, int y) {
        tx=x;
        ty=y;


    }
}
