package com.example.pojodrop;

import android.graphics.Canvas;

public class ScreenEffect extends State{
	
	boolean mIsDone;
	public ScreenEffect(GameView game){
		super(game); 
		mIsDone = false;
	}
	
	public boolean isDone(){
		return mIsDone;
	}
	
}
