package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PojoGame {

	ScoreTracker mScoreTracker;
	float mPlayTime;
	
	float mTimeScale;
	float mCanvasScaleValue;
	int mLevel; 
	GameView mView;

	public PojoGame(GameView view) {
		// TODO Auto-generated constructor stub
		mView = view;
		mTimeScale = 1.0f;
		mPlayTime = 240.0f;
		mScoreTracker = new ScoreTracker(this);
		mCanvasScaleValue = 1.0f;
		mLevel = 3;
	}
	
	public int getLevel()
	{
		return mLevel;
	}
	
	public void render(Canvas g)
	{
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		
		p.setTextSize(20.0f);
		g.drawText("Combo: " + mScoreTracker.getComboCounter(), 10, 10, p); 
	} 
	
	public float getPlayTime()
	{
		return mPlayTime;
	} 
	
	public float getTimeScale(){
		return mTimeScale;
	}
	
	public void setTimeScale(float time){
		mTimeScale = 1.0f + time;
	}
	
	public ScoreTracker getScoreTracker(){
		return mScoreTracker;
	}
	
	public GameView getView()
	{
		return mView;
	}

}
