package net.scgyong.and.cookierun.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import net.scgyong.and.cookierun.framework.game.Scene;
import net.scgyong.and.cookierun.framework.res.Metrics;
import net.scgyong.and.cookierun.game.MainScene;

public class GameView extends View implements Choreographer.FrameCallback {
    public static GameView view;
    private static final String TAG = GameView.class.getSimpleName();
    private Paint fpsPaint = new Paint();
    private long lastTimeNanos;
    private int framesPerSecond;
    private boolean initialized;
    private boolean running;
    private float elapsedtime;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        view = this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Metrics.width = w;
        Metrics.height = h;

        if (!initialized) {
            initView();
            initialized = true;
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if (!running) {
            Log.d(TAG, "Running is false on doFrame()");
            return;
        }
        long now = currentTimeNanos;
        if (lastTimeNanos == 0) {
            lastTimeNanos = now;
        }
        int elapsed = (int) (now - lastTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            lastTimeNanos = now;
            Scene game = Scene.getTopScene();
            game.update(elapsed);
            invalidate();
        }
        elapsedtime += 1;
        if(elapsedtime / 60 == 1)
        {
            if(MainScene.get().GetplayerHp()<=0)
            {
                running = false;
                //죽는걸로 게임루프 바꿔야함
            }
            MainScene.get().ReduceplayerHp();
            elapsedtime=0;
        }
        if(MainScene.get().FeverScore()>=10)
        {

            MainScene.get().FeverScoreClear();
        }
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void initView() {
        Scene.getTopScene().init();
        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return Scene.getTopScene().onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Scene.getTopScene().draw(canvas);

        canvas.drawText("Fever : " + MainScene.get().FeverScore(), 1400, 100, fpsPaint);
        canvas.drawText("" + MainScene.get().GetplayerHp(), 10, 200, fpsPaint);
    }

    public void pauseGame() {
        running = false;
    }

    public void resumeGame() {
        if (initialized && !running) {
            running = true;
            lastTimeNanos = 0;
            Choreographer.getInstance().postFrameCallback(this);
            Log.d(TAG, "Resuming game");
        }
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public boolean onBackPressed() {
        return Scene.getTopScene().handleBackKey();
    }
}
