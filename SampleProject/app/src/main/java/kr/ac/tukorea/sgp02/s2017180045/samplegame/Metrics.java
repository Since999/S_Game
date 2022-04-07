package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.res.Resources;
public class Metrics {
    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }
}