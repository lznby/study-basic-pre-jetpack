package com.lznby.jetpack.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author Lznby
 * @time 2018/10/24 16:45
 * Class Note:
 */
public class ObjectAnimationUtils {
    public static void doAnimatorSetPlayTogether(View... views) {
        ObjectAnimator bgAnimator = ObjectAnimator.ofFloat(views[0], "scaleX", 1,2);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(views[0], "scaleY", 1,2);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(bgAnimator, translateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
