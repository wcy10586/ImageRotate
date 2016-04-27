package com.wcy.imagerotate;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ObjectAnimator animator;
    private boolean canAnim = true;
    ImageView imageView;
    int degrees;
    int dealt = 90;

    float iw;
    float ih;

    float sw;
    float sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        findViewById(R.id.rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canAnim) {
                    degrees += dealt;
                    degrees = degrees % 360;
                    animator.setIntValues(degrees - dealt, degrees);
                    animator.start();
                }
            }
        });

        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                iw = imageView.getWidth();
                ih = imageView.getHeight();
                sw = getResources().getDisplayMetrics().widthPixels;
                sh = getResources().getDisplayMetrics().heightPixels;
                return true;
            }
        });

        animator = new ObjectAnimator();
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()

        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a = (Integer) animation.getAnimatedValue();
                float sx = 0;
                float sy = 0;
                if (degrees == 0) {
                    float mDe = a + dealt;
                    float mde2 = sw - ih;
                    sx = (ih + mde2 * (1 - mDe / dealt)) / ih;
                    sy = sx;
                } else if (degrees == dealt) {
                    float mDe = a;
                    float mde2 = sw - ih;
                    sx = (ih + mde2 * mDe / dealt) / ih;
                    sy = sx;
                } else if (degrees == dealt * 2) {
                    float mDe = a - dealt;
                    float mde2 = sw - ih;
                    sx = (ih + mde2 * (1 - mDe / dealt)) / ih;
                    sy = sx;
                } else {
                    float mDe = a - dealt * 2;
                    float mde2 = sw - ih;
                    sx = (ih + mde2 * mDe / dealt) / ih;
                    sy = sx;
                }
                imageView.setRotation(a);
                imageView.setScaleX(sx);
                imageView.setScaleY(sy);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                canAnim = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                canAnim = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
