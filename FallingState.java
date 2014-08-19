package com.example.pojodrop;

import android.util.Log;


public class FallingState extends BlockState{

	public static int FallingStateID = 1;
	
	int mStepsToFall;
	int mStartingPosY;
	PuzzleField mField;
	float mFallSpeedChanger;
	public FallingState(PuzzleBlock block)
	{
		super(block);
		StateID = FallingStateID;
		mStepsToFall = -1;
		mFallSpeedChanger = block.getSpeedScale();
	}
	
	public void update(float time)
	{
		Block.Move(0, (mFallSpeedChanger*PuzzleBlock.FallSpeed)*time); 
	}
	
	public void onExit()
	{
		Block.SnapToPosition(Block.getX(), Block.getY());
		Block.StateIsChanged(true);
	}
}
