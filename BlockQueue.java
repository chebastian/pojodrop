package com.example.pojodrop;

import java.util.ArrayList;
import java.util.Queue;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

public class BlockQueue {

	ArrayList<PuzzleBlock> mBlocks;
	int mNumTypes;
	public BlockQueue(int numTypes) {
		// TODO Auto-generated constructor stub
		mBlocks = new ArrayList<PuzzleBlock>();
		mNumTypes = numTypes;
	} 
	
	public void addNewBlock()
	{
		int randType = (int)Math.floor(Math.random()*mNumTypes);
		PuzzleBlock block = new PuzzleBlock(new Point());
		block.SetType(randType);
		
		mBlocks.add(block);
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
		Point renderPos = new Point(pos.x,pos.y);

		for(int i = 0; i < mBlocks.size(); i++)
		{
			renderPos.y += (i) * PuzzleBlock.BLOCK_H;
			PuzzleBlock block = mBlocks.get(i);
			block.SetPosition(renderPos.x, renderPos.y);
			block.render(g); 
			renderPos.y += blockMargin; 
		}
	}

}
