package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent.PointerProperties;

public class BubbleText extends RenderableEntity {

	Point mTarget;
	Point mOrigin;
	float mDuration;
	float mElapsedTime;
	Paint mPaint;
	String mMessage;
	float mBaseSize;
	
	public BubbleText(String text, Point start, Point end, float duration) {
		super(0, new Rect(start.x, start.y,1,1));
		mTarget = end;
		mDuration = duration;
		mElapsedTime = 0.0f;
		mOrigin = new Point(start.x,start.y);
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mMessage = text; 
		mBaseSize = 80.0f;
	}
	
	public void setPaint(Paint p){
		mPaint = p;
	}
	
	public void update(float time){
		mElapsedTime += time; 
		
		Point newPos = GameView.plert(mOrigin, mTarget, mElapsedTime / mDuration);
		if(mElapsedTime >= mDuration)
			mAlive = false;
		
		SetPosition(newPos.x, newPos.y);
	}
	
	public void render(Canvas g){
		
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setTextSize((1.0f - (mElapsedTime / mDuration)) * mBaseSize);
		mPaint.setAlpha((int) (255 * (1.0 - mElapsedTime / mDuration)));
		g.drawText(mMessage, getX(), getY(), mPaint);
	}

}
