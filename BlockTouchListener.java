package com.example.pojodrop;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class BlockTouchListener implements OnTouchListener {

	private final int DRAG_MIN_X_DIST = 20;
	protected float mLastX;
	public BlockTouchListener() {
		// TODO Auto-generated constructor stub
		mLastX = 0.0f;
	}
	
	public void onDragLeft(){
		
	}
	
	public void onDragRight(){
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		boolean result = false;
		int action = event.getAction();
		float newX = event.getX();
		

		float lastX = mLastX;
		long dt = event.getDownTime();
		
		if(dt < 100)
		{
			mLastX = newX;
			return true;
		}
		
		//if(action == MotionEvent.ACTION_MOVE)
		{
			float dx = newX - lastX;

			if(Math.abs(dx)  > DRAG_MIN_X_DIST){

				if(dx > 0)
					onDragRight();
				else
					onDragLeft(); 

				result = true;
			} 
			
		}
		
		mLastX = newX;
		return result;
	}

}
