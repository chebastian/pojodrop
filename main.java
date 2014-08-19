package com.example.pojodrop;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class Game 
{
	public int WIDTH , HEIGHT;
	boolean mKeyArr[];
	public Random Random = new Random();
	//public Applet App;

	//public BufferedImage InvaderImg, AstroidImg, TilesImg;
	public boolean Paused;
	public State mCurrentState;
	public int TILE_WIDTH, TILE_HEIGHT;
	
	public int MouseX, MouseY;
	public boolean MouseLeft, MouseRight;
	
	public Game()
	{
		
		TILE_HEIGHT = 16;
		TILE_WIDTH = 16;
		//WIDTH = size.width;
		//HEIGHT = size.height;
		mKeyArr = new boolean[256];
		Paused = false;
		/*App.addMouseListener(state);
		App.addMouseMotionListener(state);
		App.addMouseMotionListener(this);
		App.addMouseListener(this);*/
		MouseX = 0; MouseY = 0;
		MouseLeft = false;
		MouseRight = false;
		
	}
	
	public void ChangeState(State s)
	{
		/*mCurrentState.OnExit(this);
		mCurrentState = s;
		s.OnEnter(this);*/
	}
	
	public void Update(float time)
	{	
		if(!Paused)
			mCurrentState.Update(time);
		
		/*if(App.IsKeyDown('p'))
			Paused = !Paused;*/
		
	}
	
	public void Render(Canvas g)
	{
		mCurrentState.Render(g);
	}
	
	public boolean IsKeyDown(char c)
	{
		//return App.IsKeyDown(c);
		return false;
	}
	
	public boolean IsKeyPressed(char c)
	{	
		//return App.IsKeyPressed(c);
		return false;
	}
	
	public void HandleInput()
	{
		
	}

 
	
	public static void main(String[] args) {
		
		//MapEdit game = new MapEdit();
		//game.GameStart();
		
		//game.elapsedTime = 0.0f;
	}
}


