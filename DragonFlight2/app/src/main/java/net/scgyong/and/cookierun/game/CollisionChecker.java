package net.scgyong.and.cookierun.game;

import android.graphics.Canvas;

import net.scgyong.and.cookierun.framework.interfaces.BoxCollidable;
import net.scgyong.and.cookierun.framework.interfaces.GameObject;
import net.scgyong.and.cookierun.framework.res.Sound;
import net.scgyong.and.cookierun.framework.util.CollisionHelper;

import java.util.ArrayList;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    @Override
    public void update(float frameTime) {
        MainScene game = MainScene.get();
        ArrayList<GameObject> obstacles = game.objectsAt(MainScene.Layer.obstacle.ordinal());
        ArrayList<GameObject> items = game.objectsAt(MainScene.Layer.item.ordinal());
        for (GameObject item: items) {
            if (!(item instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) item)) {
                //Log.d(TAG, "Collision: " + item);
                if (item instanceof JellyItem) {
                    JellyItem jelly = (JellyItem) item;
                    if (!jelly.valid) continue;
                    Sound.playEffect(jelly.soundId());
                    if (jelly.index == 26) {
                        MainScene.get().AddplayerHp();
                    }
                    if (jelly.index == 27) {
                         player.changeBitmap();
                    }
                    else{
                        player.PlusFeverScore();
                    }
                }
                game.remove(item);
            }



        }

        for (GameObject obstacle: obstacles) {
            if (!(obstacle instanceof BoxCollidable)) {
                continue;
            }
            if (CollisionHelper.collides(player, (BoxCollidable) obstacle)) {
                //Log.d(TAG, "Collision: " + item);
                if (obstacle instanceof Obstacle) {
                    Obstacle obs = (Obstacle) obstacle;
                    MainScene.get().ReduceplayerHp();

                    game.remove(obstacle);

                }

            }



        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
