package kr.ac.tukorea.sgp02.s2017180045.DragonFlight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.GameObject;
import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180045.myapplication.R;

public class Bullet implements GameObject {
    protected float x, y;
    protected final float length;
    protected final float dx, dy;
    protected final float ex, ey;
    protected static Paint paint;
    public Bullet(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.length = Metrics.size(R.dimen.laser_length);
        float speed = Metrics.size(R.dimen.laser_speed);
        this.dx = (float) (speed * Math.cos(angle));
        this.dy = (float) (speed * Math.sin(angle));
        this.ex = (float) (length * Math.cos(angle));
        this.ey = (float) (length * Math.sin(angle));

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(Metrics.size(R.dimen.laser_width));
        }
    }
    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        float frameTime = game.frameTime;
        x += dx * frameTime;
        y += dy * frameTime;
        if(y<0)
        {
            game.remove(this);
        }
        //game.remove(this);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x + ex, y + ey, paint);
    }
}
