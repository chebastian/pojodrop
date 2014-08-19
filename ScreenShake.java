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
	
	public ScreenShake(GameView game,float duration, float intensity) {
		super(game);
		mDuration = duration;
		mIntensity = intensity;
		mElapsedTime = 0.0f;
		mNextTarget = 0.0f;
		mCurrentIndex = 0;
		mCurrentX = 0.0f;
	}
	
	public float flerp(float a, float b, float d){
		return a + ((b - a)*d);
	} 

	public void OnEnter(GameView game){
		mPoints = new float[10];
		for(int i = 0; i < mPoints.length; i++)
		{
			mPoints[i] = ((float) Math.random()  * mIntensity); 
			
			if(Math.random() > 0.5){
				mPoints[i] *= -1;
			} 
		}
	}
	
	public float getElapsedTime(){
		return mElapsedTime / mDuration;
	}
	
	public float getNextTarget()
	{
		float c = mPoints[mCurrentIndex];
		float startX = 0;
		mCurrentX = flerp(startX,c,getElapsedTime());
		return mCurrentX;
	}
	
	public void Update(float time){
		mElapsedTime += time;
		
		if(mElapsedTime >= mDuration)
		{
			mElapsedTime = 0.0f; 
			mCurrentIndex += 1;
			if(mCurrentIndex >= mPoints.length)
				mCurrentIndex = 0;
		}
	} 

	public void Render(Canvas g)
	{
		g.translate(getNextTarget(), 0);
	}
}
