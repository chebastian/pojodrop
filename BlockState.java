package com.example.pojodrop;

import android.graphics.Canvas;



public class BlockState {
	
	PuzzleBlock Block;
	int StateID;
	
	public BlockState(PuzzleBlock block)
	{
		Block = block;
		StateID = 0;
	}
	
	public void onEnter()
	{
		
	}
	
	public void update(float time)
	{
		
	}
	
	public void render(Canvas g)
	{
		
	}
	
	public void onExit()
	{
		Block.StateIsChanged(true);
	}

}
