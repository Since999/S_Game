package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.AnimSprite;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BoxCollidable;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.R;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.BitmapPool;

public class Player extends AnimSprite implements BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private RectF targetRect = new RectF();
    protected RectF boundingBox = new RectF();
    //    private float angle;
    private float dx, dy;
    private float tx, ty;
    private float elapsedTimeForFire;
    private float fireInterval = 1.0f / 10;
    public static final float FRAMES_PER_SECOND = 10.0f;
    public  float hp =10;
    private static Bitmap targetBitmap;
    public static float size;
    private float playerWidth;
    protected final float length;
//    private static Rect srcRect;

    public Player(float x, float y) {
        //super(x, y, R.dimen.fighter_radius, R.mipmap.run2);

        super(x, y, size, size, R.mipmap.run2, FRAMES_PER_SECOND, 0);
        this.length = Metrics.size(R.dimen.laser_length);
        setTargetPosition(x, y);
        //angle = -(float) (Math.PI / 2);

        targetBitmap = BitmapPool.get(R.mipmap.target);
        fireInterval = Metrics.floatValue(R.dimen.fighter_fire_interval);
        playerWidth = Metrics.size(R.dimen.laser_width);
        Log.d(TAG, "Created: fighter" + this);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    public void update() {
//        float frameTime = MainGame.getInstance().frameTime;
//        elapsedTimeForFire += frameTime;
//        if (elapsedTimeForFire >= fireInterval) {
//            //fire();
//            elapsedTimeForFire -= fireInterval;
//        }
        float frameTime = MainGame.getInstance().frameTime;

        setDstRectWithRadius();
        if (dx == 0)
            return;

        float dx = this.dx * frameTime;
        boolean arrived = false;
        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            arrived = true;
        } else {
            x += dx;
        }
        dstRect.offset(dx, 0);
        if (arrived) {
            this.dx = 0;
        }
        setDstRectWithRadius();
        float hw = playerWidth / 2;
        boundingBox.set(x - hw, y, x + hw, y + length);
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        this.ty = y;
        targetRect.set(tx - radius/2, y - radius/2,
                tx + radius/2, y + radius/2);
//        angle = (float) Math.atan2(ty - y, tx - x);
        dx = Metrics.size(R.dimen.fighter_speed);
        if (tx < x) {
            dx = -dx;
        }
//        dy = (float) (dist * Math.sin(angle));
    }

    public void fire() {
        int score = MainGame.getInstance().score.get();
        if (score > 100000) score = 100000;
        float power = 10 + score / 1000;
        Bullet bullet = Bullet.get(x, y, power);
        MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
    }
    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }
}
