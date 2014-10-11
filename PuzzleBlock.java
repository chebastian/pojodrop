package com.example.pojodrop;

import java.util.Random;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.BlurMaskFilter.Blur;


public class PuzzleBlock extends RenderableEntity{

	static int BLOCK_W = 32;
	static int BLOCK_H = 32;
	
	public int BlockType;
	BlockState CurrentState;
	public int Colour;
	public boolean Checked;
	private boolean StateChanged;
	public boolean Empty;
	public Point Index;
	public int num; 
	public int UniqueId;
	public int NextPositonY;
	public float Scale;
	protected static int UniqueIdCounter = 0;
	public static int FallSpeed = 140;
	protected float mSpeedScale;
	public int mNumNeigbours;
	protected Point mNextPosition;
	protected Point mOriginalPosition;
	protected float mElapsedMoveTime;

	public int colors[] = {Color.GREEN,Color.BLUE,Color.RED,Color.GRAY, Color.WHITE};
	static Random rand = new Random();
	boolean mNeedsToFade;
	Paint mPaint;

	public PuzzleBlock(Point index, int w, int h)
	{
		super(0,new Rect((int)index.x*w, (int)index.y*h, w, h) );
		initialize();
	}
	
	public PuzzleBlock(Point pos)
	{
		super(0,new Rect((int)pos.x, (int)pos.y, BLOCK_W, BLOCK_H) );
		initialize();
	}
	
	public PuzzleBlock(PuzzleBlock b)
	{
		super(0,b.rect);
		initialize();
	}
	
	public PuzzleBlock()
	{
		super(0,new Rect(0, 0, BLOCK_W, BLOCK_H) );
		initialize();
	}
	
	public void initialize()
	{
		BlockType = 0;
		colors = new int[]{0xFF00FFFF, 0xFF00A0FF, 0xFFFF00FF,0xFFFFFFFF};
		
		int r = rand.nextInt(3);
		Colour = colors[r];
		Checked = false; 
		BlockType = r;
		NextPositonY = -1;
		num = 0;
		UniqueId = UniqueIdCounter++;
		Empty = false;
		Scale = 1.0f;
		mNeedsToFade = false;
		mSpeedScale = 1.0f;
		CurrentState = new FallingState(this);
		mPaint = new Paint();
		//mPaint.setMaskFilter(new BlurMaskFilter(2.0f, Blur.SOLID));
		mNumNeigbours = 0;
		mNextPosition = new Point(this.rect.left, this.rect.top);
		mOriginalPosition = new Point(this.rect.left, this.rect.top);
		mElapsedMoveTime = 0;
	}
	
	public void SnapToPosition(int x, int y)
	{
		int nx = x / BLOCK_W;
		int ny = y / BLOCK_H;
		SetPosition(nx * BLOCK_W, ny * BLOCK_H);
	}
	
	public void SetType(int t)
	{
		Colour = colors[t];
		BlockType = t;
	}
	
	public void DropBlockTo(int row)
	{
		NextPositonY = row;
		ChangeState(new FallingState(this));
	}
	
	public void update(float time)
	{
		CurrentState.update(time);
		rect.right = rect.left + BLOCK_W;
		rect.bottom = rect.top + BLOCK_H;
	}
	
	public void render(Canvas g)
	{
		int x = (int)rect.left;
		int y = (int)rect.top;
		if(!Alive)
			mPaint.setColor(Color.BLACK);
		
		int drawX = x + (int)((BLOCK_W-2)*1.0f-Scale);
		int drawY = y + (int)((BLOCK_H-2)*1.0-Scale); 
		
		mPaint.setColor(Colour);
		mPaint.setStyle(Paint.Style.FILL);
//		g.drawRect(drawX, drawY, drawX+(int)((BLOCK_W-2)*Scale), drawY+(int)((BLOCK_H-2)*Scale),mPaint);
		g.drawCircle(drawX, drawY, Scale*(int)(BLOCK_W*0.5), mPaint);
		
		//mPaint.setColor((int)(Colour - Colour*0.1));
		//mPaint.setStyle(Paint.Style.STROKE);
		//mPaint.setStrokeWidth(2+(2*num));
		//g.drawRect(drawX, drawY, drawX+(int)((BLOCK_W-2)*Scale), drawY+(int)((BLOCK_H-2)*Scale),mPaint);*/
		CurrentState.render(g);
	}
	
	public void renderAt(Canvas g, Point pos){
		int x = pos.x;
		int y = pos.y;
		if(!Alive)
			mPaint.setColor(Color.BLACK);
		
		int drawX = x + (int)((BLOCK_W-2)*1.0f-Scale);
		int drawY = y + (int)((BLOCK_H-2)*1.0-Scale); 
		
		mPaint.setAntiAlias(true);
		mPaint.setColor(Colour);
		mPaint.setStyle(Paint.Style.FILL);
//		g.drawRect(drawX, drawY, drawX+(int)((BLOCK_W-2)*Scale), drawY+(int)((BLOCK_H-2)*Scale),mPaint);
		g.drawCircle(drawX, drawY, Scale*(int)(BLOCK_W*0.5), mPaint);
		
		//mPaint.setColor((int)(Colour - Colour*0.1));
		//mPaint.setStyle(Paint.Style.STROKE);
		//mPaint.setStrokeWidth(2+(2*num));
		//g.drawRect(drawX, drawY, drawX+(int)((BLOCK_W-2)*Scale), drawY+(int)((BLOCK_H-2)*Scale),mPaint);*/
		CurrentState.render(g);
		
	}
	
	public void ChangeState(BlockState state)
	{
		if(CurrentState != null)
			CurrentState.onExit();
		
		CurrentState = state;
		CurrentState.onEnter();
	}
	
	public void StateIsChanged(boolean j)
	{
		StateChanged = j;
	}
	
	public boolean StateChanged()
	{
		return StateChanged;
	}
	public boolean BlockEquals(PuzzleBlock block)
	{
		return block.UniqueId == this.UniqueId;
	}
	public boolean BlocksTypeEqual(PuzzleBlock block)
	{
		return block.BlockType == BlockType;
	}
	
	public boolean IsInState(int state)
	{
		return state == CurrentState.StateID;
	}
	
	public void StepLeft()
	{
		rect.left -= BLOCK_W;
	}
	
	public void StepRight()
	{
		rect.left += BLOCK_W;
	}
	
	public int bottom()
	{
		return rect.bottom;
	}
	
	public int top()
	{
		return rect.top;
	} 
	
	public boolean needsToFade()
	{
		return mNeedsToFade;
	}
	
	public void setToFade()
	{
		mNeedsToFade = true;
	}
	@Override
	public int compareTo(RenderableEntity another) {
		// TODO Auto-generated method stub
		return another.getY() - top();
	}
	
	public void setSpeedScale(float sc){
		mSpeedScale = sc;
	}
	
	public float getSpeedScale(){
		return mSpeedScale;
	}
	
	public void copyBlockType(int type){
		BlockType = type;
		Colour = colors[type];
	}
	
	public int getBlockType(){
		return BlockType;
	}
	
	public String toString()
	{
		String str = "";
		str = "[" + BlockType + "]";
		return str;
	}
	
}
