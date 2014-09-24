package com.example.pojodrop;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

public class BlockQueue {

	ArrayList<PuzzleBlock> mBlocks;
	int mNumTypes;
	Paint mPaint;
	Random mRand;
	int mPreviewLength;

	public BlockQueue(int numTypes) {
		// TODO Auto-generated constructor stub
		mBlocks = new ArrayList<PuzzleBlock>();
		mNumTypes = numTypes;
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(15.0f);
		mRand = new Random();
		mPreviewLength = 4;
	} 
	
	public void createRandomQueue(int queueSize)
	{
		for(int i = 0; i < queueSize; i++){
			addNewBlock();
		}
	}
	
	public void addNewBlock()
	{
		int randType = mRand.nextInt(mNumTypes);
		PuzzleBlock block = new PuzzleBlock(new Point());
		block.copyBlockType(randType);
		
		mBlocks.add(block);
	}
	
	public PuzzleBlock getNext()
	{
		return popBlock();
	}
	
	public PuzzleBlock popBlock()
	{
		PuzzleBlock block = new PuzzleBlock();
		
		try{
			block = mBlocks.get(mBlocks.size()-1);
			mBlocks.remove(mBlocks.size()-1);
		}
		catch(Exception e){
			Log.d("","Tried to get a block that does not exist");
		}

		return block;
	}
	
	public void renderAt(Canvas g, Point pos)
	{
		int blockMargin = 2;
		int textMarginBottom = 10;
		Point renderPos = new Point(pos.x,pos.y);
		g.drawText("Next: ", pos.x, pos.y + textMarginBottom, mPaint);
		
		for(int i = mBlocks.size()-1; i >= mBlocks.size()-mPreviewLength; i--)
		{
			renderPos.y += PuzzleBlock.BLOCK_H; 
			PuzzleBlock block = mBlocks.get(i);
			block.SetPosition(renderPos.x, renderPos.y);
			block.render(g); 
			renderPos.y += blockMargin;
			if(i % 2 == 0)
				renderPos.y += blockMargin*2;
		}
	}

}
