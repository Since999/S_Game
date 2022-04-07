package kr.ac.tukorea.sgp02.s2017180045.myapplication;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = kr.ac.tukorea.sgp02.s2017180045.myapplication.GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }
}
