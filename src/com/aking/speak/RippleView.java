package com.aking.speak;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * 【动画View】
 * RippleView
 * @author wangrui
 * @date    2015-5-5 下午8:48:08
 */
@SuppressLint("NewApi") 
public class RippleView extends ImageView {
	private static final int PRESSED_COLOR_LIGHTUP = 255 / 25;
	private static final int DEFAULT_PRESSED_RING_WIDTH_DIP = 4;
	private static final int ANIMATION_TIME_ID = android.R.integer.config_shortAnimTime;

	private int centerY;
	private int centerX;
	private float outerRadius;
	private float pressedRingRadius;

	private Paint circlePaint;
	private Paint focusPaint;

	private float animationProgress;

	private float pressedRingWidth;
	private int defaultColor = Color.BLACK;
	private int pressedColor;
	private ObjectAnimator pressedAnimator;
	public RippleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView(context,attrs);
	}

	public RippleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RippleView(Context context) {
		this(context, null);
	}

	private void initializeView(Context context,AttributeSet attrs) {
		this.setFocusable(true);
		this.setScaleType(ScaleType.CENTER_INSIDE);
		setClickable(true);

		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setStyle(Paint.Style.FILL);

		focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		focusPaint.setStyle(Paint.Style.FILL_AND_STROKE);

		pressedRingWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_PRESSED_RING_WIDTH_DIP, getResources()
				.getDisplayMetrics());

		int color = Color.BLACK;
		if (attrs != null) {
			final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleButton);
			color = a.getColor(R.styleable.CircleButton_cb_color, color);
			pressedRingWidth = (int) a.getDimension(R.styleable.CircleButton_cb_pressedRingWidth, pressedRingWidth);
			a.recycle();
		}

		setColor(color);

		focusPaint.setStrokeWidth(pressedRingWidth);
		final int pressedAnimationTime = getResources().getInteger(ANIMATION_TIME_ID);
		pressedAnimator = ObjectAnimator.ofFloat(this, "animationProgress", 0f, 0f);
		pressedAnimator.setDuration(pressedAnimationTime);
	}

	
	@SuppressLint("DrawAllocation") 
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawCircle(centerX, centerY, pressedRingRadius + animationProgress, focusPaint);
		canvas.drawCircle(centerX, centerY, outerRadius - pressedRingWidth, circlePaint);
		super.onDraw(canvas);
	}

	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w / 2;
		centerY = h / 2;
		outerRadius = Math.min(w, h) / 2;
		pressedRingRadius = outerRadius - pressedRingWidth - pressedRingWidth / 2;
	}

	public float getAnimationProgress() {
		return animationProgress;
	}

	public void setAnimationProgress(float animationProgress) {
		this.animationProgress = animationProgress;
		this.invalidate();
	}

	public void setColor(int color) {
		this.defaultColor = color;
		this.pressedColor = getHighlightColor(color, PRESSED_COLOR_LIGHTUP);
		circlePaint.setColor(defaultColor);
		focusPaint.setColor(defaultColor);
		focusPaint.setAlpha(75);
		this.invalidate();
	}
	
	private int getHighlightColor(int color, int amount) {
		return Color.argb(Math.min(255, Color.alpha(color)), Math.min(255, Color.red(color) + amount),
				Math.min(255, Color.green(color) + amount), Math.min(255, Color.blue(color) + amount));
	}

	public void volumChanged(int i){
		float a = (pressedRingWidth/30)*i;
		showVolumeRing(a);
	}
	private void hideVolumeRing(float volumeprogress) {
		pressedAnimator.setFloatValues(volumeprogress, 0f);
		pressedAnimator.start();
	}
	
	private void showVolumeRing(final float volume) {
		pressedAnimator.setFloatValues((outerRadius - pressedRingWidth), volume);
		pressedAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			@Override
			public void onAnimationEnd(Animator animation) {
				hideVolumeRing(volume);
			}
			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		pressedAnimator.start();
	}
	
	public void stopAnimation(){
		pressedAnimator.end();
	}
	
}
