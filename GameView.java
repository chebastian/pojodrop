package com.example.pojodrop;

import java.security.spec.MGF1ParameterSpec;

import android.content.Context;
import android.view.GestureDetector;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameView extends SurfaceView {

	public static float flerp(float a, float b, float d){
		return a + ((b - a)*d);
	} 

	public static Point plert(Point a, Point b, float d){
		float newx = flerp(a.x, b.x, d);
		float newy = flerp(a.y,b.y,d);

		Point p = new Point(0, 0);
		p.x = (int)newx;
		p.y = (int)newy;
		return p;
	}

	MainGameThread mThread;
	int mTextWidth; 
	SurfaceHolder mHolder;
	
	float mCanvasScaleValue;
	EffectManager mEffectMgr;
	EntityManager mEntityManager;
	PojoGame mGame;
	
	public GameView(Context context) {
		super(context);
		mEntityManager = new EntityManager();
		mGame = new PojoGame(this);
		mThread = new MainGameThread(this,mGame);
		mEffectMgr = new EffectManager(this); 
		mHolder = this.getHolder();
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
		
		mEffectMgr.renderEffects(canvas);
		//canvas.skew(0.2f, 0.5f);
		canvas.scale(sz, sz);
	}
	
	public float getScaleValue(Canvas canvas)
	{
		//TODO make scale usefull
		float f = (canvas.getWidth()*0.5f) / 6;
		float sc =  f / 32;
		mCanvasScaleValue = sc;
		return sc;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return super.onTouchEvent(event);
	}
	public EffectManager getEffectMgr()
	{
		return mEffectMgr;
	}
	
	public EntityManager entityManager(){
		return mEntityManager;
	}
	
	public int screenToCollumnIndex(int x){
		
		float index = 0; 
		index = this.getWidth() * mCanvasScaleValue; 
		float scX = x + (PuzzleBlock.BLOCK_W );
		
		index = scX / (PuzzleBlock.BLOCK_W * mCanvasScaleValue);
		
		return (int)index; 
	}
	
	public PojoGame getGame()
	{
		return mGame;
	}
}
