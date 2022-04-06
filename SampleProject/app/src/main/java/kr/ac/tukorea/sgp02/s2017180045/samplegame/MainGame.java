package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainGame
{
    private  static MainGame singleton;
    private static final int BALL_COUNT = 5;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Fighter fighter;
    public static float frameTime;

    public static MainGame getInstance() {
        if(singleton==null)
        {
            singleton=new MainGame();
        }
        return singleton;
    }


    public void update(int elapsedNanos)
    {
        frameTime= (float)(elapsedNanos / 1_000_000_000.0f);
        for(GameObject object : objects)
        {
            object.update();

        }
    }

    public void init()
    {

        Random random = new Random();

        for(int i = 0; i < BALL_COUNT; ++i){
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            objects.add(new Ball(dx, dy));
        }



        fighter = new Fighter();
        objects.add(fighter);

    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;

    }

    public void draw(Canvas canvas)
    {
        for(GameObject object : objects)
        {
            object.draw(canvas);
        }
    }


}
