package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Point;


//THIS IS MY LASTEST COMMENT
public class GameState extends State {

	
	PuzzleBlock block;
	PuzzleField Field; 
	float mLastKeyDown;
	
	//Font mScoreFont;
	Point mScorePosition; 

	public GameState(GameView game)
	{
		super(game);
		Field = new PuzzleField(6,13);
		block = new PuzzleBlock(new Point(100,100));
		mLastKeyDown = 0.0f;
		
		mScorePosition = new Point(300, 80);
	}
	
	public void Update(float time)
	{
		Field.update(time);
		mLastKeyDown += time;
		HandleInput();
	}
	
	public void HandleInput()
	{
	}
	
	public void Render(Canvas g)
	{
		Field.render(g);
	}
	
}
