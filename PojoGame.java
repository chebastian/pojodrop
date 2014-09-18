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
	State mCurrentState;
	PuzzleField mField;
	int mFieldWidth, mFieldHeight;
	PlayerAccount mCurrentAccount;

	public PojoGame(GameView view) {
		// TODO Auto-generated constructor stub
		mView = view;
		mTimeScale = 1.0f;
		mPlayTime = 20.0f;
		mScoreTracker = new ScoreTracker(this);
		mCanvasScaleValue = 1.0f;
		mFieldWidth = 6;
		mFieldHeight  = 13;
		mLevel = 3; 
		resetActiveField();
		mCurrentAccount = new PlayerAccount("ApplicationSebastian");
		changeState(new QuickPlayState(this)); 
	}
	
	
	public void resetActiveField()
	{
		mField = new PuzzleField(mFieldWidth, mFieldHeight);
		mField.init(this);
	}

	public int getLevel()
	{
		return mLevel;
	}
	
	public void render(Canvas g)
	{
		currentState().Render(g);
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		
		p.setTextSize(20.0f);
		g.drawText("Combo: " + mScoreTracker.getComboCounter(), 10, 10, p); 
	} 
	
	public void update(float time)
	{
		currentState().Update(time);
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
	
	public void changeState(State state){
		if(mCurrentState != null){
			mCurrentState.OnExit(getView());
		}
		
		state.OnEnter(getView());
		mCurrentState = state;
	} 
	
	public State currentState()
	{
		return mCurrentState;
	}
	
	public PuzzleField getActiveField()
	{
		return mField;
	}
	
	public String playerName()
	{
		return mCurrentAccount.Name();
	}

	public void setplayerName(String name)
	{
		
	}
}
