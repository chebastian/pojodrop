package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Rect;


public class RenderableEntity extends Entity
{
	Rect rect;
	public RenderableEntity(int id, Rect r)
	{
		super(id);
		rect = r;
	}
	
	public void Move(float x, float y)
	{
		rect.left += x;
		rect.top += y;
	}
	
	public void update(float time)
	{
		super.update(time);
	}
	
	public void render(Canvas g)
	{
		
	}
	
	public void SetPosition(int x, int y)
	{
		rect.left = x;
		rect.top = y;
	}
	
	public int getX()
	{
		return rect.left;
	}
	
	public int getY()
	{
		return rect.top;
	}
}
