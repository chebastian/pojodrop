package com.example.pojodrop;

import java.math.MathContext;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.media.audiofx.AudioEffect.OnEnableStatusChangeListener;
import android.view.animation.LinearInterpolator;

public class ScreenShake extends ScreenEffect {

	float mDuration;
	float mElapsedTime;
	float mIntensity;
	float[] mPoints;
	float mNextTarget;
	int mCurrentIndex;
	float mCurrentX;
	float mTotalTime;
	float mTimeBetweenShakes;
	
	public ScreenShake(GameView game,float duration, float intensity) {
		super(game);
		mDuration = duration;
		mIntensity = intensity;
		mElapsedTime = 0.0f;
		mNextTarget = 0.0f;
		mCurrentIndex = 0;
		mCurrentX = 0.0f;
		mTotalTime = 0.0f;
		mTimeBetweenShakes = 0.2f;
	}

	public void OnEnter(GameView game){
		mPoints = new float[8];
		for(int i = 0; i < mPoints.length; i++)
		{
			mPoints[i] = ((float) Math.random()  * mIntensity); 
			
			if(Math.random() > 0.5){
				mPoints[i] *= -1;
			} 
		}
	}
	
	public float getElapsedTime(){
		return mElapsedTime / mTimeBetweenShakes;
	}
	
	public float getNextTarget()
	{
		float c = mPoints[mCurrentIndex];
		float startX = 0;
		mCurrentX = GameView.flerp(startX,c,getElapsedTime());
		return mCurrentX;
	}
	
	public void Update(float time){
		mElapsedTime += time;
		mTotalTime += time;
		
		if(mElapsedTime >= mTimeBetweenShakes)
		{
			mElapsedTime = 0.0f; 
			mCurrentIndex += 1;
			if(mCurrentIndex >= mPoints.length)
				mCurrentIndex = 0;
		}
		
		if(mTotalTime >= mDuration)
			mIsDone = true;
	} 

	public void Render(Canvas g)
	{
		//g.translate(getNextTarget(), 0);
		//g.scale(getNextTarget(), getNextTarget());
		g.skew(getNextTarget(), getNextTarget());
		
	}
}
