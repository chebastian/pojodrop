package com.example.pojodrop;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class DragListener implements OnDragListener {

	
	float mLastX;
	public DragListener() {
		// TODO Auto-generated constructor stub
		mLastX = 0.0f;
	}
	
	public void onDragRight(){
		
	}
	
	public void onDragLeft(){
		
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		
		int action = event.getAction();
		boolean res = false;
		
		switch(action)
		{ 
                case DragEvent.ACTION_DRAG_STARTED:
                {
                	onDragging(event);
                	res = true;
                } 
                case DragEvent.ACTION_DRAG_LOCATION:{
                	onDragging(event);
                	res = true;
                }
		} 
		return res;
		
	}
	
	public void onDragging(DragEvent event){
		
		float lastX = mLastX;
		float dx = event.getX() - lastX;
		
		if(Math.abs(dx) > 20){
			if(dx < 0)
				onDragRight();
			else
				onDragLeft();
		}
		
		mLastX = event.getX();
		
	}

}
