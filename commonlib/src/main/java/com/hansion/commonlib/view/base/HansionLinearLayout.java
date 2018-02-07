package com.hansion.commonlib.view.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Description：
 * Author: Hansion
 * Time: 2017/4/27 14:29
 */
public class HansionLinearLayout extends LinearLayout {

    public HansionLinearLayout(Context paramContext) {
        super(paramContext);
    }

    public HansionLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public HansionLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public void animGo() {
        animate().alpha(0.0F).setDuration(300L).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                go();
            }
        }).start();
    }

    public void animShow() {
        animate().alpha(1.0F).setDuration(300L).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                show();
            }
        }).start();
    }

    private void go() {
        if (getVisibility() != GONE)
            setVisibility(GONE);
    }

    public void hide() {
        if (getVisibility() != INVISIBLE)
            setVisibility(INVISIBLE);
    }

    private void show() {
        if (getVisibility() != VISIBLE)
            setVisibility(VISIBLE);
    }
}
