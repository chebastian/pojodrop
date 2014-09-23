package com.example.pojodrop;

import java.util.Timer;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BlockTouchListener implements OnTouchListener {

	private final int DRAG_MIN_X_DIST = 5;
	protected float mLastX;
	protected float mOriginX;
	protected float mElapsedTime;
	protected float mLastTime;
	protected GameView mView;
	
	public BlockTouchListener(GameView game) {
		// TODO Auto-generated constructor stub
		mLastX = 0.0f;
		mView = game;
		mOriginX = 0.0f;
		mElapsedTime = 0.0f;
		mLastTime = 0.0f;
	}
	
	public void onDragLeft(){
		
	}
	
	public void onDragRight(){
		
	}
	
	public void setColumnValue(int x){
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		boolean result = true;

		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			Log.d("touch",""+event.getX());
			mOriginX = event.getX();
			mLastX = event.getX();
			break;
		}

		case MotionEvent.ACTION_MOVE:
		{
			float newX = event.getX();
			float d = mOriginX - newX;
			float oldD = mLastX - newX;
			
			int w = mView.getWidth();
			int wstep = w / 6;
			
			if(Math.abs(d) > wstep)
			{
				if(d < 0)
					this.onDragRight();
				else
					this.onDragLeft();
				
				mOriginX = newX;
			}

			int step = (int) (d % wstep);
			int oldStep = (int) (oldD % wstep);
			
			mLastX = newX;

			break;
		}

		case MotionEvent.ACTION_UP:
		{
			Log.d("","");
			break;
		}
		}

		return result;
	}

}
