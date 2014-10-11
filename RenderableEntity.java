package com.example.pojodrop;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;


public class RenderableEntity extends Entity implements Comparable<RenderableEntity>
{
	Rect rect;
	int mLayerZ;
	boolean mAlive;
	public RenderableEntity(int id, Rect r)
	{
		super(id);
		rect = r;
		mAlive = true;
		mLayerZ = 0; 
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
	
	public int getZ()
	{
		return mLayerZ;
	}
	
	public Point lertPoint(Point origin, Point target, float speed)
	{
		Point result = new Point();
		
		return result;
	}
	
	public void moveTowards(Point origin, Point pos, float speed)
	{
		Point dir = new Point( pos.x - origin.x, pos.y - origin.y); 
		
		PointF normalDir = new PointF();
		float len = (float) Math.sqrt((dir.x * dir.x) + (dir.y * dir.y));
		normalDir.x = dir.x / len; 
		normalDir.y = dir.y / len; 
		float sp = 200.0f;
		rect.left += (normalDir.x) * (speed*sp);
		rect.top += (normalDir.y) * (speed*sp);
	}
	
	public boolean isWithinRange(Point p, float range)
	{
		float dist = distanceTo(p);
		return dist < range;
	}
	
	public float distanceTo(Point p)
	{ 
		Point pos = new Point(rect.left, rect.top); 
		Point dir = new Point( p.x - pos.x, p.y - pos.y ); 
		
		float len = (float) Math.sqrt((dir.x * dir.x) + (dir.y * dir.y));
		return len;
	}
	
	public Point getCenter()
	{
		return new Point(rect.left + (rect.width()/2), rect.top +( rect.height()/2));
	}

	@Override
	public int compareTo(RenderableEntity other) {
		// TODO Auto-generated method stub
		return other.getZ() - getZ();
	}
}
