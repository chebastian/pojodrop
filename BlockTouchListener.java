package com.example.pojodrop;

import java.util.Timer;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BlockTouchListener implements OnTouchListener {

	private final int DRAG_MIN_X_DIST = 5;
	protected float mLastY;
	protected float mOriginX;
	protected float mElapsedTime;
	protected float mLastTime;
	protected GameView mView;
	
	public BlockTouchListener(GameView game) {
		// TODO Auto-generated constructor stub
		mView = game;
		mOriginX = 0.0f;
		mElapsedTime = 0.0f;
		mLastTime = 0.0f;
	}
	
	public void onDragLeft(){
		
	}
	
	public void onDragRight(){
		
	}
	
	public void onDragUp()
	{
		
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
			mLastY = event.getY();
			break;
		}

		case MotionEvent.ACTION_MOVE:
		{
			handleXMovement(event);
			handleYMovement(event);
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
	
	protected void handleXMovement(MotionEvent event)
	{
			float newX = event.getX();
			float d = mOriginX - newX;
			
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
	}
	
	protected void handleYMovement(MotionEvent event)
	{
			float newY = event.getY();
			float d = mLastY - newY;
			
			int h = mView.getHeight();
			int hstep = h / 20;
			
			if(Math.abs(d) > hstep)
			{
				if(d > 0)
					this.onDragUp();
				
				mLastY = newY;
			}

	}

}
