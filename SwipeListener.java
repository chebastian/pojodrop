package com.example.pojodrop;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeListener implements OnTouchListener{

	private GestureDetector mDetector;
	
	public SwipeListener(Context con)
	{
		mDetector = new GestureDetector(con,new GestureListener());
	}

	public void onSwipeLeft()
	{
		Log.d("","SWIPE LEFT");
	}

	public void onSwipeRight()
	{
		Log.d("","Swipe RIGHT");
	}
	
	public void onSwipeUp()
	{
		
	}
	
	public void onSwipeDown()
	{
		
	}
	
	public void onDoubleTapped()
	{
		
	}
	
	public boolean onTouch(View v,MotionEvent evt){
		return mDetector.onTouchEvent(evt);
	}
	
	
	private final class GestureListener extends SimpleOnGestureListener{
		
		private static final int SWIPE_TRESHOLD = 20;
		private static final int SWIPE_VELOCITY_TRESHOLD = 50;
		
		float mLastXValue;
		@Override
		public boolean onDown(MotionEvent evt){
			float last = mLastXValue;
			
			mLastXValue = evt.getX();
			return true;
		}
		
		public boolean onUp(MotionEvent evt)
		{
			mLastXValue = 0;
			return true;
		}
		
		public boolean onDoubleTap(MotionEvent evt)
		{
			onDoubleTapped();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY)
		{
			boolean res = false;
			
			try{
				float dx = e1.getX() - e2.getX();
				float dy = e1.getY() - e2.getY();
				if(Math.abs(dx) > Math.abs(dy))
				{
					if(Math.abs(dx) > SWIPE_TRESHOLD && Math.abs(velX) > SWIPE_VELOCITY_TRESHOLD)
					{
						if(dx > 0)
							onSwipeLeft();
						else
							onSwipeRight(); 
						res = true;
					}
				}
				else
				{
					if(Math.abs(dy) > SWIPE_TRESHOLD && Math.abs(velY) > SWIPE_VELOCITY_TRESHOLD)
					{
						if(dy > 0)
							onSwipeUp();
						else
							onSwipeDown();
					}
				}
			}
			catch(Exception e){
				
			}
			return res;
		}
		
	}

}
