package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PlaytimeBar extends RenderableEntity {

	float mElapsedTime;
	int mMaxTime;
	Paint mBarPaint;
	Paint mInactiveBarPaint;
	int mInitialHeight;
	boolean mActive;
	
	
	public PlaytimeBar(int id, Point pos, int height, int max) {
		super(id, new Rect(pos.x, pos.y, pos.x + 20,pos.y + height));
		mInitialHeight = height;
		mMaxTime = max;
		mElapsedTime = 0.0f;

		mBarPaint = new Paint();
		mBarPaint.setColor(Color.RED);
		mInactiveBarPaint = new Paint();
		mInactiveBarPaint.setColor(Color.GRAY); 
		
		mActive = true;
	}
	
	public void update(float time)
	{
		if(mActive)
                mElapsedTime += time;

		updateBarHeight(time);
	}
	
	public void updateBarHeight(float time)
	{
		float newHeight = mInitialHeight * (1.0f - (mElapsedTime/mMaxTime));
		this.rect.top = (int) (this.rect.bottom - (newHeight));
	}
	
	public void render(Canvas g)
	{
		Paint current = mBarPaint;
		
		if(!mActive)
			current = mInactiveBarPaint;

		g.drawRect(this.rect, current);
	}
	
	public void increaseTime(float amount)
	{
		mElapsedTime -= amount;
		if(mElapsedTime <= 0.0f)
		{
			mElapsedTime = 0;
		}
	}
	
	public void setActive(boolean a)
	{
		mActive = a;
	}
	
	public boolean isDone()
	{
		return mElapsedTime >= mMaxTime;
	}

}
