package com.example.pojodrop;

import android.graphics.Canvas;

public class ScreenEffect extends State{
	
	boolean mIsDone;
	GameView mView;
	public ScreenEffect(PojoGame game, GameView view){
		super(game); 
		mView = view;
		mIsDone = false;
	}
	
	public boolean isDone(){
		return mIsDone;
	}
	
}
