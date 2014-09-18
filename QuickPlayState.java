package com.example.pojodrop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class QuickPlayState extends State {

	PuzzleField Field;
	float mPlayTime;
	Paint mTextPaint;
	PlaytimeBar mTimeBar;
	
	public QuickPlayState(PojoGame game) {
		super(game);
		mGame.resetActiveField();
	}
	
	public void OnEnter(GameView game)
	{ 
		mTextPaint = new Paint();
		mTextPaint.setTextSize(20.0f);
		mTextPaint.setFakeBoldText(true);
		mTextPaint.setColor(Color.WHITE);
		
		mPlayTime = mGame.getPlayTime();
		mTimeBar = new PlaytimeBar(0, new Point(300,100), 100, (int)mPlayTime);
		mGame.getView().entityManager().addEntity(mTimeBar);

		mGame.getView().setOnTouchListener(new SwipeListener(mGame.getView().getContext(),mGame){
			public void onSwipeLeft(){
				if(mGame.getActiveField().CanMoveActiveBlockInDirection(-1))
					mGame.getActiveField().MoveActiveBlock(-PuzzleBlock.BLOCK_W, 0);
			}
			
			public void onSwipeRight(){
				if(mGame.getActiveField().CanMoveActiveBlockInDirection(1))
					mGame.getActiveField().MoveActiveBlock(PuzzleBlock.BLOCK_W, 0);
			}
			
			public void onSwipeDown(){
				mGame.getActiveField().DropActiveBlock();
				//mGame.getActiveField().AddNewActiveBlock();
			}
			
			public void onSwipeUp(){
				if(mGame.getActiveField().canRotateActiveBlock())
					mGame.getActiveField().RotateActiveBlock();
			}
			
			public void onDoubleTapped(){
				if(mGame.getActiveField().canRotateActiveBlock())
					mGame.getActiveField().RotateActiveBlock();
				
				//kFieldLogger log = new FieldLogger();
				//log.saveSnapshotOfField(mState.Field);
			}
			
			public void setCollumnValue(int x){
				if(!mGame.getActiveField().hasActiveBlocks())
						return; 
				
				if(mGame.getActiveField().CanMoveActiveBlockToCollumn(x))
				{
					int curX = mGame.getActiveField().CollumnPosition(mGame.getActiveField().ActiveBlock.get(0));
					int dx =  x - curX;
					if(mGame.getActiveField().CanMoveActiveBlockInDirection(dx))
						mGame.getActiveField().MoveActiveBlock(dx * PuzzleBlock.BLOCK_W, 0);
					
				}
				
			}
		}); 
	}
	
	public void Update(float time)
	{
		mGame.getActiveField().update(time);
		updatePlayTime(time);

		if(!mGame.getActiveField().hasFadingBlocks() && mGame.getActiveField().hasActiveBlocks())
			mTimeBar.setActive(true);
		else
			mTimeBar.setActive(false);

		if(gameIsOver()){
			mGame.mView.mThread.setRunnint(false);
			mGame.changeState(new PresentScoreState(mGame));

		}
	}
	
	public void Render(Canvas g)
	{
		mGame.getActiveField().render(g);
		renderCountDown(g);
		mGame.getActiveField().getQueue().renderAt(g, new Point(164,0));
		Paint p = new Paint();
		p.setTextSize(20.0f);
		p.setColor(Color.WHITE);
		g.drawText("Score: " + mGame.getScoreTracker().getScore(), 300, 80, p);
	}
	
	public void renderCountDown(Canvas g)
	{
		mTextPaint.setTextSize(20.0f);
		g.drawText(""+mPlayTime, 100, 100, mTextPaint);
	}
	
	public void updatePlayTime(float time){
		mPlayTime -= time;
	}
	
	public boolean gameIsOver()
	{
		return mTimeBar.isDone();
	}

	public void OnExit(GameView game)
	{
		
	}

}
