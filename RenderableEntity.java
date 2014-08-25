package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Rect;


public class RenderableEntity extends Entity
{
	Rect rect;
	boolean mAlive;
	public RenderableEntity(int id, Rect r)
	{
		super(id);
		rect = r;
		mAlive = true;
	}
	
	public void Move(float x, float y)
	{
		rect.left += x;
		rect.top += y;
	}
	
	public void update(float time)
	{
		super.update(time);
		rect.bottom = rect.top + rect.height();
		rect.right = rect.left + rect.width();
	}
	
	public void render(Canvas g)
	{
		
	}
	
	public void SetPosition(int x, int y)
	{
		rect.left = x;
		rect.top = y;
		rect.right = rect.left + PuzzleBlock.BLOCK_W;
		rect.bottom = rect.top + PuzzleBlock.BLOCK_H;
	}
	
	public int getX()
	{
		return rect.left;
	}
	
	public int getY()
	{
		return rect.top;
	}
	
	public boolean isAlive(){
		return mAlive;
	}
}
