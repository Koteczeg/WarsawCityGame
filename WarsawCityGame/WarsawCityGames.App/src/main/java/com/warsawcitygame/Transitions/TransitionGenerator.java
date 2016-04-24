package com.warsawcitygame.Transitions;

import android.graphics.RectF;

public interface TransitionGenerator
{
    Transition generateNextTransition(RectF drawableBounds, RectF viewport);
}