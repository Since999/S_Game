package kr.ac.tukorea.sgp02.s2017180045.DragonFlight.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.GameObject;
import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.GameView;
import kr.ac.tukorea.sgp02.s2017180045.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180045.myapplication.R;

public class MainGame {
    private static MainGame singleton;
    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }
    private static final int BALL_COUNT = 10;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private Fighter fighter;
    public float frameTime;

    public void init() {
        Random random = new Random();
        float min = Metrics.size(R.dimen.ball_speed_min);
        float max = Metrics.size(R.dimen.ball_speed_max);
        float diff = max - min;
        for (int i = 0; i < BALL_COUNT; i++) {
//            int dx = random.nextInt(10) + 5;
//            int dy = random.nextInt(10) + 5;
            float dx = random.nextFloat() * diff + min;
            float dy = random.nextFloat() * diff + min;


        }

        float fx = Metrics.width / 2;
        float fy = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(fx, fy);
        gameObjects.add(fighter);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                fighter.setTargetPosition(x, y);

                return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        for (GameObject gobj : gameObjects) {
            gobj.draw(canvas);
        }
    }

    public void update(int elapsedNanos) {
        frameTime = (float) (elapsedNanos / 1_000_000_000f);
        for (GameObject gobj : gameObjects) {
            gobj.update();
        }
    }

    public void add(GameObject gameObject) {
        GameView.view.post(new Runnable()
        {
            @Override
            public void run() {
                gameObjects.add(gameObject);

            }
        });

    }

    public void remove(GameObject gameObject)
    {
        GameView.view.post(new Runnable()
        {
            @Override
            public void run() {
                gameObjects.remove(gameObject);

            }
        });

    }
}
