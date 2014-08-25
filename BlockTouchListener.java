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
	protected GameView mGame;
	
	public BlockTouchListener(GameView game) {
		// TODO Auto-generated constructor stub
		mLastX = 0.0f;
		mGame = game;
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
		boolean result = false;
		int action = event.getAction();
		float newX = event.getX();
		
		int colX = mGame.screenToCollumnIndex((int)event.getRawX());
		Log.d("collumnX",""+colX);
		setColumnValue(colX);


		float lastX = mLastX;
		long dt = event.getDownTime();
		Log.d("","dt"+dt);
		float t = System.currentTimeMillis();
		
		//THE FIRST PRESS ON THE SCREEN
		if(action == MotionEvent.ACTION_DOWN){ 			
			mOriginX = event.getX(); 
			mLastTime = t;
			mElapsedTime = 0.0f;
			return true;
		}

		//ANY MOVE THAT HAPPENS AFTER THE ACTION DOWN
		if(action == MotionEvent.ACTION_MOVE)
		{
			float current = (t - mLastTime)/1000.0f;
			mElapsedTime += current;
			
			if(mElapsedTime >= 0.2)
			{
				float dx = newX - lastX; 
				Log.d("dist of drag: ", ""+dx);
				if(Math.abs(dx)  > DRAG_MIN_X_DIST){

					if(dx > 0)
						onDragRight();
					else
						onDragLeft(); 

					result = true; 
				} 
				mElapsedTime = 0.0f;
			} 
		}
		
		mLastX = newX;
		mLastTime = t;
		return result;
	}

}
