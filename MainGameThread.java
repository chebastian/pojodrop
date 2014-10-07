package com.example.pojodrop;

import android.R.menu;
import android.annotation.SuppressLint;
import android.content.ReceiverCallNotAllowedException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.view.SurfaceHolder;

class MainGameThread extends AsyncTask<String, String, String> {

	public static final int WIN_WIDTH = 800;
	public static final int WIN_HEIGHT = 600;
	public static final float SESSION_PLAY_TIME = 60.0f;
	long lastTime = 0;
	float elapsedTime = 0.0f;

	private GameView mGameView;
	private PojoGame mGame;

	float x = 0; 
	int DESIRED_FPS = 30;
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
	String TAG = "GameThread";
	boolean mHasExited;
	
	//GameState mState;
//	QuickPlayState mState;

	public MainGameThread(GameView gameview, PojoGame game)
	{
		mGame = game;
		mGameView = gameview;

		lastTime = 0;
		lastTime = System.currentTimeMillis();
		mRunning = false;
		/*mState = new QuickPlayState(game);
		mState.OnEnter(game);
		mState.Field.init(game);*/
		mGameTime = 0.0f; 
		
/*		mGame.setOnTouchListener(new BlockTouchListener(mGame){

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
		});*/
	}
	
	public void run()
	{
		mHasExited = false;
		GameLoop();
	}

	public void stopRunning()
	{
		setRunnint(false);
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
			mGameView.getEffectMgr().updateEffects(elapsed);

			try
			{
				mCanvas = mGameView.getHolder().lockCanvas();
				synchronized (mGameView.getHolder()) {
					mGameView.draw(mCanvas);
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
				boolean res = true;
				//while(res)
				{
					synchronized (SERIAL_EXECUTOR) {
					}if(mCanvas != null)
					{ 
						mGameView.getHolder().unlockCanvasAndPost(mCanvas);
						res = mCanvas == null;
					}
						
				}
			}

			lastTime = time; 
		}
		
		Log.d(TAG,"Ended main loop");
		mHasExited = true;
	}
	
	public boolean hasExited()
	{
		return mHasExited;
	}


	float seconds = 0.0f;
	public void GameUpdate(float time)
	{
		seconds += time;
		x += time * 25.0f;
		mTotalTime += time;
		mGame.getView().entityManager().updateEntitys(time);
		mGame.getView().entityManager().cleanDeadEntitys(); 
		mGameTime += time;
		mGame.setTimeScale(mGameTime / SESSION_PLAY_TIME );
		
		mGame.update(time);
		mGame.currentState().Update(time);
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
		mGame.getView().entityManager().renderEntitys(g);
		mGame.currentState().Render(g);
	}
	
	public void clearGameScreen(Canvas g,int color)
	{
		/*Paint p = new Paint();
		p.setColor(color);
		g.drawRect(new Rect(0,0,g.getWidth(),g.getHeight()), p); */
		g.drawColor(color);
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		run();
		return null;
	}
}