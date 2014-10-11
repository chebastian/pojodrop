package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Point;



public class FadingState extends BlockState{
	
	static int FadingStateID = 4; 
	float mFadeTime; 
	float mElapsedTime;
	Point mOrigin;
	Point mTarget;
	
	public FadingState(PuzzleBlock block,Point target)
	{
		super(block);
		mTarget = target;
		mFadeTime = 0.5f;
		mElapsedTime = 0.0f;
		StateID = FadingStateID;
		mOrigin = new Point(block.getX(), block.getY());
	}
	
	public void update(float time)
	{
		boolean isDone = Block.isWithinRange(mTarget, 5.0f);
		mElapsedTime += time;
//		Block.moveTowards(mOrigin, new Point(mOrigin.x + 100, mOrigin.y + 100), time);
		Point p = GameView.plert(mOrigin, mTarget, mElapsedTime/mFadeTime);
		//Block.moveTowards(mOrigin, mTarget, time);
		Block.SetPosition(p.x, p.y);

		if(mElapsedTime > mFadeTime)
		{
			onExit();
		}
			
	}
	
	public void render(Canvas g)
	{
		Block.Scale = (float) Math.sin((Math.PI) * (1.0f - (mElapsedTime / mFadeTime)));
	}
	
	public void onExit()
	{
		this.Block.Kill();
	}

}
