package com.example.pojodrop;

import android.graphics.Canvas;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PresentScoreState extends State {

	Paint mScoreTextPaint;
	Point mScorePos;
	Button mRetryButton;
	
	public PresentScoreState(PojoGame game) {
		super(game);
		mScoreTextPaint = new Paint();
		mScoreTextPaint.setColor(Color.WHITE);
		mScoreTextPaint.setTextSize(30.0f);
		mScorePos = new Point(20,100);
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
		
		mRetryButton = new Button(mGame.getView().getContext());
		mRetryButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		mRetryButton.setText("RETRY?");
	
		LinearLayout layout = new LinearLayout(mGame.getView().getContext());
		layout.addView(mRetryButton);
		
		ServerInterface server = new ServerInterface();
		String str = server.getServerList();
		server.addScore("personA",mGame.getScoreTracker().getScore());
		
		String list = server.getServerList();
		list = list;
	}
	
	public void Render(Canvas g)
	{
		g.drawText("Your score was: " + mGame.getScoreTracker().getScore(), mScorePos.x, mScorePos.y, mScoreTextPaint);
	}

}
