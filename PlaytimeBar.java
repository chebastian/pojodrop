package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class PlaytimeBar extends RenderableEntity {

	float mElapsedTime;
	int mMaxTime;
	Paint mBarPaint;
	Paint mInactiveBarPaint;
	int mInitialHeight;
	boolean mActive;
	
	
	public PlaytimeBar(int id, Point pos, int height, int max) {
		//super(id, new Rect(pos.x, pos.y, pos.x + 20,pos.y + height));
		super(id, new Rect(pos.x, pos.y, pos.x + height,pos.y + height));
		mInitialHeight = height;
		mMaxTime = max;
		mElapsedTime = 0.0f;

		mBarPaint = new Paint();
		mBarPaint.setColor(Color.RED);
		mInactiveBarPaint = new Paint();
		mInactiveBarPaint.setColor(Color.GREEN); 
		
		mActive = true;
	}
	
	public void update(float time)
	{
		if(mActive)
                mElapsedTime += time;

		//updateBarHeight(time);
	}
	
	public void updateBarHeight(float time)
	{
		float newHeight = mInitialHeight * (1.0f - (mElapsedTime/mMaxTime));
		//this.rect.top = (int) (this.rect.bottom - (newHeight));
		this.rect.right = (int)(this.rect.left + newHeight);
	}
	
	public float elapsedInPercent()
	{
		return mElapsedTime/mMaxTime;
	}
	
	public void render(Canvas g)
	{
		Paint current = mBarPaint;
		
		if(!mActive)
			current = mInactiveBarPaint;
		
		current.setAntiAlias(true);

		g.drawArc(new RectF(this.rect), 0.0f, (float)(360*(1.0f-elapsedInPercent())), true, current);
		
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(30.0f);
		g.drawText("Time Remaining:"+(mMaxTime-mElapsedTime), this.rect.left, this.rect.top, textPaint);
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
