package kr.ac.tukorea.sgp02.s2017180045.DragonFlight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.BitmapPool;
import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.Sprite;
import kr.ac.tukorea.sgp02.s2017180045.myapplication.R;

public class Fighter extends Sprite {
    private static final String TAG = Fighter.class.getSimpleName();
    private RectF targetRect = new RectF();


    private float dx, dy;
    private float tx, ty;
    private float elapsedTimeForFire;
    private float fireInterval = 1.0f / 10.0f;

    private static Bitmap targetBitmap;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);

        targetBitmap = BitmapPool.get(R.mipmap.target);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
        if (dx != 0 && dy != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        float dx = this.dx * frameTime;
        boolean arrived = false;

        elapsedTimeForFire += frameTime;
        if (elapsedTimeForFire>=fireInterval)
        {
            fire();
            elapsedTimeForFire -= fireInterval;
        }

        if (dx == 0 && dy == 0)
            return;

        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            arrived = true;
        } else {
            x += dx;
        }

        dstRect.offset(dx, dy);
        if(arrived)
        {
            this.dx =0;
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = ty;
        targetRect.set(tx - radius/2, ty - radius/2,
                tx + radius/2, ty + radius/2);
        dx = Metrics.size(R.dimen.fighter_speed);



    }

    public void fire() {
        Bullet bullet = new Bullet(x, y, (float)(-Math.PI/2));
        MainGame.getInstance().add(bullet);
    }
}
