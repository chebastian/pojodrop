package com.example.pojodrop;

import android.R.menu;
import android.annotation.SuppressLint;
import android.content.ReceiverCallNotAllowedException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;

class MainGameThread extends Thread {

	public static final int WIN_WIDTH = 800;
	public static final int WIN_HEIGHT = 600;
	public static final float SESSION_PLAY_TIME = 60.0f;
	long lastTime = 0;
	float elapsedTime = 0.0f;

	private GameView mGame;

	float x = 0; 
	int DESIRED_FPS = 60;
	int SKIPPED_TICKS = 1000 / DESIRED_FPS;
	int MAX_SKIPPED = 10;
	float MAX_FPS = 1.0f / 30.0f;
	long nextFrame = 0;
	float lastElapsed = 1.0f / 60.0f;
	boolean mRunning;
	Canvas mCanvas;

	int frame = 0;
	int fps = 0;
	float mTotalTime;
	float mGameTime;
	
	GameState mState;

	public MainGameThread(GameView game)
	{
		mGame = game;
		lastTime = 0;
		lastTime = System.currentTimeMillis();
		mRunning = false;
		mState = new GameState(game);
		mState.Field.init(game);
		mGameTime = 0.0f;
		
		/*mGame.setOnTouchListener(new SwipeListener(mGame.getContext(),mGame){
			public void onSwipeLeft(){
				if(mState.Field.CanMoveActiveBlockInDirection(-1))
					mState.Field.MoveActiveBlock(-PuzzleBlock.BLOCK_W, 0);
			}
			
			public void onSwipeRight(){
				if(mState.Field.CanMoveActiveBlockInDirection(1))
					mState.Field.MoveActiveBlock(PuzzleBlock.BLOCK_W, 0);
			}
			
			public void onSwipeDown(){
				mState.Field.DropActiveBlock();
				mState.Field.AddNewActiveBlock();
			}
			
			public void onSwipeUp(){
				if(mState.Field.canRotateActiveBlock())
					mState.Field.RotateActiveBlock();
			}
			
			public void onDoubleTapped(){
				if(mState.Field.canRotateActiveBlock())
					mState.Field.RotateActiveBlock();
			}
			
			public void setCollumnValue(int x){
				if(!mState.Field.hasActiveBlocks())
						return; 
				
				if(mState.Field.CanMoveActiveBlockToCollumn(x))
				{
					int curX = mState.Field.CollumnPosition(mState.Field.ActiveBlock.get(0));
					int dx =  x - curX;
					if(mState.Field.CanMoveActiveBlockInDirection(dx))
						mState.Field.MoveActiveBlock(dx * PuzzleBlock.BLOCK_W, 0);
					
				}
				
			}
		}); */
		
		mGame.setOnTouchListener(new BlockTouchListener(mGame){

			public void setColumnValue(int x){
				if(!mState.Field.hasActiveBlocks())
						return; 
				
				if(mState.Field.CanMoveActiveBlockToCollumn(x))
				{
					int curX = mState.Field.CollumnPosition(mState.Field.ActiveBlock.get(0));
					int dx =  x - curX;
					if(mState.Field.CanMoveActiveBlockInDirection(dx))
						mState.Field.MoveActiveBlock(dx * PuzzleBlock.BLOCK_W, 0);
					
				}
			}
		});
	}
	
	public void run()
	{
		GameLoop();
	}

	public void setRunnint(boolean r)
	{
		mRunning = r;
	}

	public boolean isRunning()
	{
		return mRunning;
	}

	public void GameLoop()
	{
		while(mRunning)
		{
			long time = System.currentTimeMillis();
			float elapsed = (System.currentTimeMillis() - lastTime )/ 1000.0f;

			long sinceLast = time - lastTime;
			long toNext = time + SKIPPED_TICKS;
			long toWait = 15;
			mCanvas = null;
			
			if(elapsed >= MAX_FPS || elapsed < 0.0f)
			{
				elapsed = lastElapsed;
			}
			else
			{
				float current = (System.currentTimeMillis() - lastTime)/1000.0f;
				float desire = MAX_FPS;
				float wait = desire - current;
				toWait = (long)(wait*1000.0);
				lastElapsed = MAX_FPS;
			}

			elapsedTime = elapsed;
			GameUpdate(elapsed);
			mState.Update(elapsed);
			mGame.mEffectMgr.updateEffects(elapsed);

			try
			{
				mCanvas = mGame.getHolder().lockCanvas();
				synchronized (mGame.getHolder()) {
					mGame.draw(mCanvas);
				}
				
				try {
					Thread.sleep(toWait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				lastTime = time;
				GameDraw(mCanvas);
			}
			finally{
				if(mCanvas != null)
					mGame.getHolder().unlockCanvasAndPost(mCanvas);
			}

			lastTime = time;

		}
	}

	float seconds = 0.0f;
	public void GameUpdate(float time)
	{
		seconds += time;
		x += time * 25.0f;
		mTotalTime += time;
		mGame.entityManager().updateEntitys(time);
		mGame.entityManager().cleanDeadEntitys(); 
		mGameTime += time;
		mGame.setTimeScale(mGameTime / SESSION_PLAY_TIME );
	}

	public void GameDraw(Canvas g)
	{ 
		frame++;
		
		Paint p = new Paint();
		clearGameScreen(g, Color.BLACK);

		if(mTotalTime >= 1.0f)
		{
			mTotalTime = 0.0f;
			fps = frame;
			frame = 0;
		}

		p.setColor(Color.RED);
		g.drawText("Combo: " + mGame.getScoreTracker().getComboCounter(),10,100,p);
		g.drawText("Score: " + mGame.getScoreTracker().getScore(), 10, 80, p);
		g.drawText("timescale "+mGame.getTimeScale(), 100, 200, p);
		mState.Render(g);
		mGame.entityManager().renderEntitys(g);
	}
	
	public void clearGameScreen(Canvas g,int color)
	{
		Paint p = new Paint();
		p.setColor(color);
		g.drawRect(new Rect(0,0,g.getWidth(),g.getHeight()), p); 
	}
}