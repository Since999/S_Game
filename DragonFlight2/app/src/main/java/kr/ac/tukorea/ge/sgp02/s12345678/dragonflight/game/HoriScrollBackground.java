package kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.game;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Metrics;
import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.Sprite;

public class HoriScrollBackground extends Sprite {
    private final float speed;
    private final int height;
    public HoriScrollBackground(int bitmapResId, float speed) {
        super(Metrics.width / 2, Metrics.height / 2,
                Metrics.width, Metrics.height, bitmapResId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);
        this.speed = speed;
    }

    @Override
    public void update() {
        this.y += speed * MainGame.getInstance().frameTime;
//        if (y > Metrics.height) {
//            y = 0;
//        }
//        setDstRect(Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        int curr = (int)y % Metrics.width;
        if (curr > 0) curr -= Metrics.width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr +Metrics.width,  Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += Metrics.width;
        }
    }
}
