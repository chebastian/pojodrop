package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SimpleParticle extends RenderableEntity {

	Point mTarget;
	Point mOrigin;
	float mDuration;
	float mElapsedTime;
	Paint mPaint;
	float mBaseSize;

	public SimpleParticle(Point start, Point end, float duration) {
		super(0, new Rect(start.x, start.y,1,1));
		mTarget = end;
		mDuration = duration;
		mElapsedTime = 0.0f;
		mOrigin = new Point(start.x,start.y);
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
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
		
		mPaint.setAlpha((int) (255 * (1.0 - mElapsedTime / mDuration)));
		g.drawCircle(getX(), getY(), this.mBaseSize, mPaint);
	}


}
