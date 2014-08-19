package com.example.pojodrop;


public class IdleState extends BlockState{

	public static int IdleStateID = 2;
	public IdleState(PuzzleBlock block)
	{
		super(block);
		StateID = IdleStateID;
	}
	
	public void update(float time)
	{
		super.update(time);
	}
}
