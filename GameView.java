package com.example.pojodrop;

import android.content.Context;
import android.view.GestureDetector;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView {

	
	MainGameThread mThread;
	int mTextWidth; 
	SurfaceHolder mHolder;
	ScoreTracker mScoreTracker;
	ScreenShake mShake;
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mThread = new MainGameThread(this);
		mScoreTracker = new ScoreTracker();
		mShake = new ScreenShake(this, 0.2f, 50.0f);
		mShake.OnEnter(this);
		
		mHolder = getHolder();
		mHolder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				boolean tryAgain = true;
				while(tryAgain){
					try {
						mThread.join();
						tryAgain = false;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				mThread.setRunnint(true);
				mThread.start();

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) { 
			}
		});
		setFocusable(true);
		
		mTextWidth = 100;
		setWillNotDraw(false);
		
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	   // Try for a width based on our minimum
	   int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
	   int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

	   // Whatever the width ends up being, ask for a height that would let the pie
	   // get as big as it can
	   int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
	   int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

	   setMeasuredDimension(w, h);
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		float sz  = getScaleValue(canvas);
		float x = (float)Math.random();
		//canvas.translate(10*x, 0);
		mShake.Render(canvas);
		canvas.scale(sz, sz);
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		
		canvas.drawText("Combo: " + mScoreTracker.getComboCounter(), 10, 10, p);
		
		
	}
	
	public float getScaleValue(Canvas canvas)
	{
		//TODO make scale usefull
		float f = (canvas.getWidth()*0.5f) / 6;
		float sc =  f / 32;
		return sc;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int mask = event.getActionMasked();
		switch(mask)
		{
		case MotionEvent.ACTION_MOVE:
			
		}
		return super.onTouchEvent(event);
	} 
	
	public ScoreTracker getScoreTracker(){
		return mScoreTracker;
	}
}
