package com.firestarterstagss.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.firestarterstagss.R;

public class SetAnimation {

    static public void ButtonAnim(Context ctx, View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(ctx, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }

    static public void ShakeAnim(Context ctx, View view) {
        final Animation myAnim = AnimationUtils.loadAnimation(ctx, R.anim.shake);
        view.startAnimation(myAnim);
    }
}
