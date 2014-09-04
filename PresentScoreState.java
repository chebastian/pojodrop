package com.example.pojodrop;

import android.graphics.Canvas;
import android.view.View.OnTouchListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class PresentScoreState extends State {

	Paint mScoreTextPaint;
	Point mScorePos;
	public PresentScoreState(PojoGame game) {
		super(game);
		mScoreTextPaint = new Paint();
		mScoreTextPaint.setColor(Color.WHITE);
		mScoreTextPaint.setTextSize(30.0f);
		mScorePos = new Point(20,100);
		// TODO Auto-generated constructor stub
	}
	
	public void OnEnter(GameView view)
	{
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				mGame.changeState(new QuickPlayState(mGame));
				return true;
			}
		});
	}
	
	public void Render(Canvas g)
	{
		g.drawText("Your score was: " + mGame.getScoreTracker().getScore(), mScorePos.x, mScorePos.y, mScoreTextPaint);
	}

}
