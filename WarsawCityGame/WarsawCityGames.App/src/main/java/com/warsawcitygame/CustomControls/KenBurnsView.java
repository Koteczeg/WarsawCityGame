package com.warsawcitygame.CustomControls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.warsawcitygame.R;
import com.warsawcitygame.Transitions.RandomTransitionGenerator;
import com.warsawcitygame.Transitions.Transition;
import com.warsawcitygame.Transitions.TransitionGenerator;


public class KenBurnsView extends ImageView {

    /** Delay between a pair of frames at a 100 FPS frame rate. */
    private static final long FRAME_DELAY = 1000 / 100;
    /** Matrix used to perform all the necessary transition transformations. */
    private final Matrix mMatrix = new Matrix();
    /** The TransitionGenerator implementation used to perform the transitions between
     * rects. The default TransitionGenerator is RandomTransitionGenerator. */
    private TransitionGenerator mTransGen = new RandomTransitionGenerator();
    /** A TransitionListener to be notified when a transition starts or ends. */
    private TransitionListener mTransitionListener;
    /** The ongoing transition. */
    private Transition mCurrentTrans;
    /** The rect that holds the bounds of this view. */
    private final RectF mViewportRect = new RectF();
    /** The rect that holds the bounds of the current {@link Drawable}. */
    private RectF mDrawableRect;
    /** The progress of the animation, in milliseconds. */
    private long mElapsedTime;
    /** The time, in milliseconds, of the last animation frame.
     * This is useful to increment {@link #mElapsedTime} regardless
     * of the amount of time the animation has been paused. */
    private long mLastFrameTime;
    /** Controls whether the the animation is running. */
    private boolean mPaused;
    /** Indicates whether the parent constructor was already called.
     * This is needed to distinguish if the image is being set before
     * or after the super class constructor returns. */
    private boolean mInitialized;

    public KenBurnsView(Context context) {
        this(context, null);
        super.setImageResource(R.drawable.splash_background);
        handleImageChange();
    }
    public KenBurnsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        super.setImageResource(R.drawable.splash_background);
        handleImageChange();
    }
    public KenBurnsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInitialized = true;
        super.setScaleType(ScaleType.MATRIX);
        super.setImageResource(R.drawable.splash_background);
        handleImageChange();
    }
    @Override
    public void setScaleType(ScaleType scaleType) {
    }
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        switch (visibility) {
            case VISIBLE:
                resume();
                break;
            default:
                pause();
                break;
        }
    }
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        handleImageChange();
    }
    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        handleImageChange();
    }
    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        handleImageChange();
    }
    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        handleImageChange();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        restart();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable d = getDrawable();
        if (!mPaused && d != null) {
            if (mDrawableRect.isEmpty()) {
                updateDrawableBounds();
            } else if (hasBounds()) {
                if (mCurrentTrans == null) {
                    startNewTransition();
                }
                if (mCurrentTrans.getDestinyRect() != null) {
                    mElapsedTime += System.currentTimeMillis() - mLastFrameTime;
                    RectF currentRect = mCurrentTrans.getInterpolatedRect(mElapsedTime);

                    float widthScale = mDrawableRect.width() / currentRect.width();
                    float heightScale = mDrawableRect.height() / currentRect.height();
                    float currRectToDrwScale = Math.min(widthScale, heightScale);
                    float currRectToVpScale = mViewportRect.width() / currentRect.width();
                    float totalScale = currRectToDrwScale * currRectToVpScale;

                    float translX = totalScale * (mDrawableRect.centerX() - currentRect.left);
                    float translY = totalScale * (mDrawableRect.centerY() - currentRect.top);

                    mMatrix.reset();
                    mMatrix.postTranslate(-mDrawableRect.width() / 2, -mDrawableRect.height() / 2);
                    mMatrix.postScale(totalScale, totalScale);
                    mMatrix.postTranslate(translX, translY);

                    setImageMatrix(mMatrix);

                    if (mElapsedTime >= mCurrentTrans.getDuration()) {
                        fireTransitionEnd(mCurrentTrans);
                        //startNewTransition();
                    }
                } else {
                    fireTransitionEnd(mCurrentTrans);
                }
            }
            mLastFrameTime = System.currentTimeMillis();
            postInvalidateDelayed(FRAME_DELAY);
        }
        super.onDraw(canvas);
    }
    /**
     * Generates and starts a transition.
     */
    private void startNewTransition() {
        if (!hasBounds()) {
            throw new UnsupportedOperationException("Can't start transition if the " +
                    "drawable has no bounds!");
        }
        mCurrentTrans = mTransGen.generateNextTransition(mDrawableRect, mViewportRect);
        mElapsedTime = 0;
        mLastFrameTime = System.currentTimeMillis();
        fireTransitionStart(mCurrentTrans);
    }
    /**
     * Creates a new transition and starts over.
     */
    public void restart() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) {
            throw new UnsupportedOperationException("Can't call restart() when view area is zero!");
        }

        updateViewport(width, height);
        updateDrawableBounds();

        if (hasBounds()) {
            startNewTransition();
        }
    }
    /**
     * Checks whether this view has bounds.
     */
    private boolean hasBounds() {
        return !mViewportRect.isEmpty();
    }
    /**
     * Fires a start event on mTransitionListener
     * @param transition the transition that just started.
     */
    private void fireTransitionStart(Transition transition) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener.onTransitionStart(transition);
        }
    }
    /**
     * Fires an end event on {@link #mTransitionListener};
     * @param transition the transition that just ended.
     */
    private void fireTransitionEnd(Transition transition) {
        if (mTransitionListener != null && transition != null) {
            mTransitionListener.onTransitionEnd(transition);
        }
    }
    /**
     * Sets the {@link TransitionGenerator} to be used in animations.
     * @param transgen the {@link TransitionGenerator} to be used in animations.
     */
    public void setTransitionGenerator(TransitionGenerator transgen) {
        mTransGen = transgen;
        if (hasBounds()) {
            startNewTransition();
        }
    }
    /**
     * Updates the viewport rect. This must be called every time the size of this view changes.
     * @param width the new viewport with.
     * @param height the new viewport height.
     */
    private void updateViewport(float width, float height) {
        mViewportRect.set(0, 0, width, height);
    }
    /**
     * Updates the drawable bounds rect. This must be called every time the drawable
     * associated to this view changes.
     */
    private void updateDrawableBounds() {
        if (mDrawableRect == null) {
            mDrawableRect = new RectF();
        }
        Drawable d = getDrawable();
        if (d != null) {
            mDrawableRect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        }
    }
    /**
     * This method is called every time the underlying image
     * is changed.
     */
    private void handleImageChange() {
        updateDrawableBounds();
/* Don't start a new transition if this event
was fired during the super constructor execution.
The view won't be ready at this time. Also,
don't start it if this view size is still unknown. */
        if (mInitialized && hasBounds()) {
            startNewTransition();
        }
    }

    public void setTransitionListener(TransitionListener transitionListener) {
        mTransitionListener = transitionListener;
    }


    /**
     * Pauses the Ken Burns Effect animation.
     */
    public void pause() {
        mPaused = true;
    }


    /**
     * Resumes the Ken Burns Effect animation.
     */
    public void resume() {
        mPaused = false;
// This will make the animation to continue from where it stopped.
        mLastFrameTime = System.currentTimeMillis();
        invalidate();
    }


    /**
     * A transition listener receives notifications when a transition starts or ends.
     */
    public interface TransitionListener {
        /**
         * Notifies the start of a transition.
         * @param transition the transition that just started.
         */
        void onTransitionStart(Transition transition);

        /**
         * Notifies the end of a transition.
         * @param transition the transition that just ended.
         */
        void onTransitionEnd(Transition transition);
    }
}