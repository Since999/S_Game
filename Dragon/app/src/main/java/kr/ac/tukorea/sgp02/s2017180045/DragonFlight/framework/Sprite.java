package kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Sprite implements GameObject {
    protected Bitmap bitmap;
    protected RectF dstRect = new RectF();
    protected float x, y, radius;
    public Sprite(float x, float y, int radiusDimenResId, int bitmapResId) {
        this.x = x;
        this.y = y;
        this.radius = Metrics.size(radiusDimenResId);
        dstRect.set(x - radius, y - radius, x + radius, y + radius);
        bitmap = BitmapPool.get(bitmapResId);
    }

    public Sprite(float x, float y, float w, float h, int bitmapResId) {
        this.x = x;
        this.y = y;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);
        bitmap = BitmapPool.get(bitmapResId);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
