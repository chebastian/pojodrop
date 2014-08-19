package com.example.pojodrop;

import android.graphics.Canvas;



public class FadingState extends BlockState{
	
	static int FadingStateID = 4; 
	float mFadeTime; 
	float mElapsedTime;
	
	public FadingState(PuzzleBlock block)
	{
		super(block);
		mFadeTime = 0.5f;
		mElapsedTime = 0.0f;
		StateID = FadingStateID;
	}
	
	public void update(float time)
	{
		mElapsedTime += time;
		if(mFadeTime <= mElapsedTime)
		{
			onExit();
		}
			
	}
	
	public void render(Canvas g)
	{
		Block.Scale = 1.0f - (mElapsedTime / mFadeTime);
	}
	
	public void onExit()
	{
		this.Block.Kill();
	}

}
