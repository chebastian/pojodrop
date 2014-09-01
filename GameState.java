package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Point;


//THIS IS MY LASTEST COMMENT
public class GameState extends State {

	PuzzleBlock block;
	PuzzleField Field; 
	float mLastKeyDown; 
	Point mScorePosition; 
	float mPlayingTime;
	BlockQueue mBlockQueue;

	public GameState(PojoGame game)
	{
		super(game);
		Field = new PuzzleField(6,13);
		block = new PuzzleBlock(new Point(100,100));
		mLastKeyDown = 0.0f; 
		mScorePosition = new Point(300, 80);
		mPlayingTime = 0.0f;
		mBlockQueue = new BlockQueue(3); 
	}
	
	public void Update(float time)
	{
		updatePlayingTime(time);
		Field.update(time);
		HandleInput();
	}
	
	public void updatePlayingTime(float time)
	{
		mPlayingTime += time;
	}
	
	public void HandleInput()
	{
	}
	
	public void Render(Canvas g)
	{
		Field.render(g);
		int w = g.getWidth();
		int h = g.getHeight();
		Field.getQueue().renderAt(g, new Point((int)(h*0.1f),(int)(w*0.1)));
	}
	
	public BlockQueue getBlockQueue()
	{
		return mBlockQueue;
	}
	
}
