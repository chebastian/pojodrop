package com.example.pojodrop;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SwipeListener implements OnTouchListener{

	PojoGame mGame;
	private GestureDetector mDetector;
	private BlockTouchListener mTouchListener;
	
	public SwipeListener(Context con, PojoGame game)
	{
		mGame = game;
		mDetector = new GestureDetector(con,new GestureListener(game));
		mTouchListener = new BlockTouchListener(game.getView()){
			public void onDragLeft()
			{
				onSwipeLeft();
			}
			public void onDragRight()
			{
				onSwipeRight();
			}
		};
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
	
	public void onDragLeft(){
		
	}
	
	public void onDragRight(){
		
	}
	
	public void setCollumnValue(int x){
		
	}
	
	public boolean onTouch(View v,MotionEvent evt){
		boolean res = mDetector.onTouchEvent(evt);
		boolean res2 = mTouchListener.onTouch(v, evt);
		return res || res2;
	}
	
	
	private final class GestureListener extends SimpleOnGestureListener{
		
		private static final int SWIPE_TRESHOLD = 20;
		private static final int DRAG_TRESHOLD = 20;
		private static final int SWIPE_VELOCITY_TRESHOLD = 30;
		
		float mLastXValue; 
		public GestureListener(PojoGame view){
			super();
		}

		public boolean onDown(MotionEvent evt){
			return true;
		} 
		
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float velX, float velY){
			boolean res = false;
			
			try{
				float dx = e1.getX() - e2.getX();
				float dy = e1.getY() - e2.getY();
				if(Math.abs(dx) > Math.abs(dy))
				{
					if(Math.abs(dx) > DRAG_TRESHOLD){
						int colX = mGame.getView().screenToCollumnIndex((int)e1.getRawX());
						//setCollumnValue(colX);
						res = true;
					} 
				}
			}
			catch(Exception e){
				
			}
			return res;
		}
		
		public boolean onDoubleTap(MotionEvent evt)
		{
			onDoubleTapped();
			return true;
		}
		
		public boolean onSingleTapConfirmed(MotionEvent e)
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
				/*		if(Math.abs(dx) > SWIPE_TRESHOLD && Math.abs(velX) > SWIPE_VELOCITY_TRESHOLD)
					{
						if(dx > 0)
							onSwipeLeft();
						else
							onSwipeRight(); 
						res = true;
					}*/
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
