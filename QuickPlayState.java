package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class QuickPlayState extends State {

	PuzzleField Field;
	float mPlayTime;
	Paint mTextPaint;
	
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
				mGame.getActiveField().AddNewActiveBlock();
			}
			
			public void onSwipeUp(){
				if(mGame.getActiveField().canRotateActiveBlock())
					mGame.getActiveField().RotateActiveBlock();
			}
			
			public void onDoubleTapped(){
				if(mGame.getActiveField().canRotateActiveBlock())
					mGame.getActiveField().RotateActiveBlock();
				
				GetServerListTask lst = new GetServerListTask();
				//lst.execute("");
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
		
		if(gameIsOver()){
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
		g.drawText("Combo: " + mGame.getScoreTracker().getComboCounter(),10,100,p);
		g.drawText("Score: " + mGame.getScoreTracker().getScore(), 10, 80, p);
		g.drawText("timescale "+mGame.getTimeScale(), 100, 200, p);
	}
	
	public void renderCountDown(Canvas g)
	{
		g.drawText(""+mPlayTime, 0, 100, mTextPaint);
	}
	
	public void updatePlayTime(float time){
		mPlayTime -= time;
	}
	
	public boolean gameIsOver()
	{
		return mPlayTime <= 0;
	}

	public void OnExit(GameView game)
	{
		
	}

}
