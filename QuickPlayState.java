package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class QuickPlayState extends State {

	PuzzleField Field;
	float mPlayTime;
	Paint mTextPaint;
	
	public QuickPlayState(PojoGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		Field = new PuzzleField(6, 12);
	}
	
	public void OnEnter(PojoGame game)
	{ 
		mTextPaint = new Paint();
		mTextPaint.setTextSize(20.0f);
		mTextPaint.setFakeBoldText(true);
		mTextPaint.setColor(Color.WHITE);
		
		mPlayTime = game.getPlayTime();
	}
	
	public void Update(float time)
	{
		Field.update(time);
		updatePlayTime(time);
	}
	
	public void Render(Canvas g)
	{
		Field.render(g);
		renderCountDown(g);
		Field.getQueue().renderAt(g, new Point(164,0));
	}
	
	public void renderCountDown(Canvas g)
	{
		g.drawText(""+mPlayTime, 0, 100, mTextPaint);
	}
	
	public void updatePlayTime(float time){
		mPlayTime -= time;
	}

	public void OnExit(GameView game)
	{
		
	}

}
