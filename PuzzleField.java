package com.example.pojodrop;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class PuzzleField extends RenderableEntity {
	
	int FIELD_WIDTH, FIELD_HEIGHT;

	float SpawnRate = 0.5f;
	float mElapsedTime;
	PuzzleBlock[][] Blocks;
	List<PuzzleBlock> ActiveBlock;
	List<ArrayList<PuzzleBlock>> BlockMap;
	LinkedList<LinkedList<PuzzleBlock>> Map;
	
	BlockField mField;
	int mCurrentRot;
	int mLastClusterSize;
	GameView mGameView;
	PojoGame mGame;
	BlockQueue mBlockQueue;
	
	boolean mNeedToUpdate;
	
	public PuzzleField(int w, int h)
	{
		super(0,new Rect());
		FIELD_WIDTH = w;
		FIELD_HEIGHT = h;
		mElapsedTime = 0.0f;
		mLastClusterSize = 0;
		
		Blocks = new PuzzleBlock[FIELD_WIDTH][FIELD_HEIGHT];
		ActiveBlock = new ArrayList<PuzzleBlock>();
		BlockMap = new ArrayList<ArrayList<PuzzleBlock>>();
		Map = new LinkedList<LinkedList<PuzzleBlock>>();
		
		for(int i = 0; i < FIELD_WIDTH; i++)
		{
			BlockMap.add(new ArrayList<PuzzleBlock>());
		}
		
		mCurrentRot = 0;
		
		mField = new BlockField(w, h);
		mNeedToUpdate = false;
	}
	
	public void init(PojoGame game)
	{
		mGame = game;
		mBlockQueue = new BlockQueue(3);
		mBlockQueue.createRandomQueue(100);
	}
	
	public void update(float time)
	{
		//HandleBlockSpawn(time);
		boolean mLastTimeNeed = mNeedToUpdate;
		
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			b.update(time);
			handleBlockReachedBottom(b);
		}
		
		if(activeBlockReachedBottom())
		{
			//DropActiveBlock();
			DropActiveBlock();
		}

		

		updateBlocksInField(time);
		
		boolean blocksHasGoneIdle = mLastTimeNeed && !hasBlockInState(DroppState.DroppStateID);
		if(activeBlockReachedBottom() || blocksHasGoneIdle)
		{
			lookForBlockClusters(); 
		}

		handleBlockRemoval();
		mNeedToUpdate = hasBlockInState(DroppState.DroppStateID);
		if(ActiveBlock.size() <= 0 && !mNeedToUpdate && !hasFadingBlocks())
		{
			AddNewActiveBlock(); 
			mGame.getScoreTracker().clearComboCounter();
		}
	}
	
	public void updateBlocksInField(float time)
	{
		boolean updateFade = !hasBlockInState(DroppState.DroppStateID);
		boolean hasACombo = false;
		int blockCounter = 0;
		Point blockPos = new Point();
		for(int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				PuzzleBlock bl = GetBlock(i, j);
				bl.update(time); 
				if(updateFade && bl.needsToFade() && !bl.IsInState(FadingState.FadingStateID))
				{ 
					bl.ChangeState(new FadingState(bl)); 
					blockPos = new Point(bl.getX(),bl.getY());
					hasACombo = true;
					blockCounter++;
				}
			}
		}
		
		if(hasACombo)
		{
			mGame.getScoreTracker().increaseComboCounter();
			int combo = mGame.getScoreTracker().getComboCounter();
			if(combo > 1){
				mGame.getView().entityManager().addEntity(new BubbleText(""+combo + " COMBO!", new Point(0,200), new Point(blockPos.x,0), 2.0f));
			}
			int score = mGame.getScoreTracker().increaseScore(blockCounter);
			mGame.getView().entityManager().addEntity(new BubbleText("+" + score, blockPos, new Point(blockPos.x,0), 1.5f));
			//mGame.getView().getEffectMgr().addEffect(new ScreenShake(mGame, 0.3f, 0.5f));
			//mGame.getEffectMgr().addEffect(new ScreenShake(mGame, 0.5f, -1.2f)); 
		}
	}
	
	public boolean hasFadingBlocks()
	{
		for(int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				if(GetBlock(i, j).needsToFade())
					return true;
			}
		}
		return false;
	}
	
	public boolean lookForBlockClusters()
	{ 
		boolean clusterFound = false;
		for(int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				PuzzleBlock block = BlockMap.get(i).get(j);

				//block.update(time);

				//if(BlockMap.get(i).get(j).StateChanged())
				{
					HandleBlockClustering(block);
					block.StateIsChanged(false);
					clusterFound = true;
				}
				
			}
		}
			
		return clusterFound;
	}
	
	public void handleBlockRemoval()
	{
		for(int i = 0; i < BlockMap.size(); i++)
		{
			if(collumnHasDeadBlocks(i))
			{
				UpdateCollumn(i);
				removeDeadBlocksInCollumn(i);
			}
		}
	}
	
	public boolean collumnHasDeadBlocks(int col)
	{
		for(int j = 0; j < BlockMap.get(col).size(); j++)
		{
			if(!BlockMap.get(col).get(j).Alive)
				return true;
		}
		
		return false;
	}
	
	public void removeDeadBlocksInCollumn(int col)
	{ 
		for(int j = 0; j < BlockMap.get(col).size(); j++)
		{
			if(BlockMap.get(col).get(j).Alive == false)
				BlockMap.get(col).remove(j);
		}
	}
	
	public void UpdateCollumn(int c)
	{
		for(int i = 0; i < BlockMap.get(c).size(); i++)
		{
			PuzzleBlock b = BlockMap.get(c).get(i);
			if(!b.Alive || b.IsInState(DroppState.DroppStateID))
				continue;
			
			if(NumEmptySpacesBelowBlock(b) > 0)
			{ 
				//b.ChangeState(new FallingState(b,NumEmptySpacesBelowBlock(b)));
				b.ChangeState(new DroppState(b,NumEmptySpacesBelowBlock(b)));
			}
		}
	}
	
	public LinkedList<PuzzleBlock> GetAdjecentBlocksOfSameType(PuzzleBlock block)
	{
		LinkedList<PuzzleBlock> total = new LinkedList<PuzzleBlock>();
		LinkedList<PuzzleBlock> found = new LinkedList<PuzzleBlock>();
		
		Point dirs[] = { new Point(1,0),new Point(0,1),
				new Point(-1,0), new Point(0,-1) };
		
		block.Checked = true;
		
		for(int j = 0; j < dirs.length; j++)
		{
			Point dir = dirs[j];
			Point newPos = PosToIndex(new Point(block.getX(),block.getY()));
			newPos.x += dir.x;
			newPos.y += dir.y;
			
			if(IsBlock(newPos.x, newPos.y))
			{
				PuzzleBlock newBlock = GetBlock(newPos.x, newPos.y);
				if(newBlock.BlocksTypeEqual(block))
				{
					if(newBlock.Checked == false && newBlock.IsInState(IdleState.IdleStateID))
						found.add(newBlock);	
				}
			}
		}
		
		for(int i = 0; i < found.size(); i++)
		{
			PuzzleBlock newBlock = found.get(i);
			total.add(newBlock);
			LinkedList<PuzzleBlock> news = GetAdjecentBlocksOfSameType(newBlock);
			total.addAll(news);
		}
		
		//total.add(block);
		block.Checked = false;
		
		return total;
	}
	
	public void HandleBlockClustering(PuzzleBlock block)
	{
		int count = 0;
		boolean clusterFound = false;
		
		Point dirs[] = { new Point(1,0),new Point(0,1),
				new Point(-1,0), new Point(0,-1) };
		
		LinkedList<PuzzleBlock> found = GetAdjecentBlocksOfSameType(block);
		
		block.num = found.size();
		
		if(found.size() >= 3)
		{
			for(int i = 0; i < found.size(); i++)
			{
				PuzzleBlock bl = found.get(i);
				//bl.ChangeState(new FadingState(bl,mGame));
				mLastClusterSize = found.size() + 1;
				bl.setToFade();
			}
			//block.ChangeState(new FadingState(block,mGame));
			block.setToFade();
			clusterFound = true;
		}
		
		block.Checked = false;
	}
	
	
	public void handleBlockReachedBottom(PuzzleBlock block)
	{
		if(BlockReachedBottom(block))
		{
			block.ChangeState(new IdleState(block));
			//HandleBlockClustering(block);
		}
	}

	public void HandleBlockSpawn(float time)
	{ 
		if(activeBlockReachedBottom())
		{
			//AddNewBlock();
			AddNewActiveBlock();
			mElapsedTime = 0.0f;
		} 
	}
	
	public boolean activeBlockReachedBottom()
	{ 
		int counter = 0;
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			if(BlockReachedBottom(b))
			{
				counter += 1;
			}
		}
		
		if(lowerBlockHasLanded())
			return true;

		return counter == ActiveBlock.size();
	}
	
	/*
	 * THIS WHOLE FUNCTION IS NOT USED ANYMORE REMOVE 
	 */
	
	
	/*public void AddNewBlock()
	{
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			if(ActiveBlock.get(i).IsInState(FallingState.FallingStateID) )
				return;
		}
		
		Point start = new Point(0,0);
		PuzzleBlock block = new PuzzleBlock(start);
		
		start.x += PuzzleBlock.BLOCK_W;
		PuzzleBlock block2 = new PuzzleBlock(start);
		ActiveBlock.clear();
		ActiveBlock.add(block);
		ActiveBlock.add(block2);
		mCurrentRot = 0;
	}*/
	
	public boolean hasBlockInState(int stateId)
	{ 
		for(int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				if(GetBlock(i, j).IsInState(stateId))
                        return true;
			}
		}
		return false;
	}

	
	public boolean RemoveBlock(PuzzleBlock block)
	{
		for(int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				PuzzleBlock b = BlockMap.get(i).get(j);
				if(b.BlockEquals(block))
				{
					BlockMap.get(i).remove(j);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void AddBlockInMap(PuzzleBlock block)
	{
		int col = CollumnPosition(block);
		BlockMap.get(col).add(block);
	}
	
	public void AddBlock(PuzzleBlock block,int x, int y)
	{
		if(IsInField(x, y))
			Blocks[x][y] = block;
	}
	
	public void AddBlock(int type, int xindex, int yindex)
	{
		if(IsInField(xindex * PuzzleBlock.BLOCK_W, yindex * PuzzleBlock.BLOCK_H))
		{
			PuzzleBlock b = new PuzzleBlock();
			b.SetPosition(xindex * PuzzleBlock.BLOCK_W, yindex * PuzzleBlock.BLOCK_H);
			addBlockIntoArray(b);
		}
	}
	
	private void addBlockIntoArray(PuzzleBlock b)
	{
		int col = CollumnPosition(b);
		int row = RowPosition(b);
		Blocks[col][row] = b;
	}
	
	public void AddBlockToBottom(PuzzleBlock block)
	{
		int col = CollumnPosition(block);
        PositionBlockAtCollumnTop(block, col);
		
		AddBlockInMap(block);
		
		HandleBlockClustering(block);
	}
	
	public boolean BlockReachedBottom(PuzzleBlock block)
	{
		int col = CollumnPosition(block);
		int cH = GetCollumnHeightInPixels(col);
		
		return block.top() >= cH; 
	}
	
	public boolean lowerBlockHasLanded()
	{
		int idleY = 0;
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			if(b.IsInState(IdleState.IdleStateID))
			{
				idleY = b.top(); 
					for(int j = 0; j < ActiveBlock.size(); j++){
						if(ActiveBlock.get(j).top() < idleY)
							return true;
						
					}
			}
		}
		
		return false;
	}
	
	public void PositionBlockAtCollumnTop(PuzzleBlock block, int col)
	{
		int colH = GetCollumnHeightInPixels(col);
		block.SetPosition(block.getX(), colH);
	}
	
	public Point PosToIndex(Point pos)
	{
		Point p = new Point(0,0);
		
		p.x = (pos.x - rect.left) / PuzzleBlock.BLOCK_W;
		p.y = FIELD_HEIGHT - ((pos.y - rect.top) / PuzzleBlock.BLOCK_H);
		
		return p;
	}
	
	public boolean IsInField(int x, int y)
	{
		if(x < 0 || x >= FIELD_WIDTH  ||
				y < 0 || y >= FIELD_HEIGHT )
			return false;
		
		return true;
	}
	
	public boolean IsBlock(int x, int y)
	{
		if(!IsInField(x, y))
			return false;
		
		if(BlockMap.get(x).size() <= y)
			return false;
		
		return true;
	}
	
	public PuzzleBlock GetBlock(int x, int y)
	{
		return BlockMap.get(x).get(y);
	}
	
	public void render(Canvas g)
	{
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			b.render(g);
		}
		
		for( int i = 0; i < BlockMap.size(); i++)
		{
			for(int j = 0; j < BlockMap.get(i).size(); j++)
			{
				BlockMap.get(i).get(j).render(g);
			}
		}
	}
	
	public void MoveActiveBlock(float xdist, float ydist)
	{
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);			
			b.Move(xdist, ydist);
			if(!CanMoveActiveBlockToCollumn(CollumnPosition(ActiveBlock.get(i))))
			{
				b.Move(-xdist,-ydist);
			}
		}
	}
	
	public boolean CanMoveActiveBlockInDirection(int x)
	{
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			if(b.IsInState(IdleState.IdleStateID))
				return false;
			
			if( !CanMoveBlockToCollumn(b, CollumnPosition(b) + x) )
				return false;
		}
		
		return true;
	}
	
	public boolean canRotateActiveBlock()
	{ 
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			if(ActiveBlock.get(i).IsInState(IdleState.IdleStateID))
				return false; 
		}
		return true;
	}
	
	public void AddNewActiveBlock()
	{
		Point start = new Point(PuzzleBlock.BLOCK_W*2, 0);
		PuzzleBlock block = new PuzzleBlock(start);
		
		start.x += PuzzleBlock.BLOCK_W;
		PuzzleBlock block2 = new PuzzleBlock(start);
		float tsc = mGame.getTimeScale();
		block.setSpeedScale(tsc);
		block2.setSpeedScale(tsc);
		
		block.copyBlockType(mBlockQueue.getNext().getBlockType());
		block2.copyBlockType(mBlockQueue.getNext().getBlockType());
		
		ActiveBlock.clear();
		ActiveBlock.add(block);
		ActiveBlock.add(block2); 
		mCurrentRot = 0;
	}
	
	public void setActiveBlockFallSpeedScale(float sc)
	{
		for(int i = 0; i < ActiveBlock.size(); i++){
			ActiveBlock.get(i).setSpeedScale(sc);
		}
	}
	public void DropActiveBlock()
	{
		Collections.sort(ActiveBlock);
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			b.ChangeState(new IdleState(b));
			AddBlockToBottom(b);
		} 
		ActiveBlock.clear();
	}
	
	public void RotateActiveBlock()
	{
		
		Point[] dirs = {new Point(0,-1),
								new Point(1,0),
								new Point(0,1),
								new Point(-1,0)};
		
		int mNextRot = mCurrentRot+1;
		if(mNextRot >= dirs.length)
			mNextRot = 0;
			
		if(!CanMoveActiveBlockInDirection(dirs[mNextRot].x))
			return;

		mCurrentRot++;
		
		if(mCurrentRot > 3)
			mCurrentRot = 0;
		
		if(ActiveBlock.size() <= 0)
			return;
		
		PuzzleBlock parent = ActiveBlock.get(0);
		for(int i = 1; i < ActiveBlock.size(); i++)
		{
			PuzzleBlock b = ActiveBlock.get(i);
			b.SetPosition((int)(parent.getX() + (PuzzleBlock.BLOCK_W*i)*dirs[mCurrentRot].x),
					(int)(parent.getY() + (PuzzleBlock.BLOCK_H*i)*dirs[mCurrentRot].y));
		}
	}
	
	public boolean CanMoveBlockToCollumn(PuzzleBlock block, int col)
	{
		int row = RowPosition(block);
		int colPos = CollumnPosition(block);
		
		if(colPos >= FIELD_WIDTH-1 || colPos < 0)
			return false;
		
		if(col >= FIELD_WIDTH-1 || col < 0)
			return false;
		
		int sz = BlockMap.get(col).size();
		if(sz <= 0)
			return true;
		
		PuzzleBlock b = BlockMap.get(col).get(sz-1);
		
		if(block.bottom() >= b.top())
			return false;
		
		return true;
	}
	
	public boolean CanMoveActiveBlockToCollumn(int col)
	{
		for(int i = 0; i < ActiveBlock.size(); i++)
		{
			if(!CanMoveBlockToCollumn(ActiveBlock.get(i), col))
				return false;
		}
		return true;
	}
	
	public int NumEmptySpacesBelowBlock(PuzzleBlock block)
	{
		int num = 0;
		
		int x = CollumnPosition(block);
		int y = RowPosition(block);
		for(int i = y; i >= 0; i--)
		{
			if(!IsBlock(x, i))
				continue;
			
			if(GetBlock(x, i).Alive == false)
				num++;
		}
		
		return num;
	}
	
	public int GetCollumnHeightInPixels(int col)
	{
		int height =  (int)FieldHeightInPixels() - (BlockMap.get(col).size() * PuzzleBlock.BLOCK_H);
		
		return height;
	}
	
	public int CollumnPosition(PuzzleBlock block)
	{
		return ((block.getX() + PuzzleBlock.BLOCK_W/2)- rect.left) / PuzzleBlock.BLOCK_W;
	}
	
	public int RowPosition(PuzzleBlock block)
	{
		return (FIELD_HEIGHT-1) - (((block.top() + PuzzleBlock.BLOCK_H/2) - rect.top) / PuzzleBlock.BLOCK_H);
	}
	
	public int CollumnSize(int col)
	{
		int count = 0; 
		count = BlockMap.get(col).size();
		
		return count;
	}
	
	public int NumberOfAliveBlocksInColumn(int col)
	{
		int c = 0;
		
		for(int i = 0; i < CollumnSize(col); i++)
		{
			if(GetBlock(col, i).Alive)
			{
				c += 1;
			}
		}
		return c;
	}
	
	public float FieldHeightInPixels()
	{
		return FIELD_HEIGHT * PuzzleBlock.BLOCK_H;
	}
	
	public int getLastClusterSize()
	{
		return mLastClusterSize;
	}
	
	public boolean hasActiveBlocks(){
		return ActiveBlock.size() > 0;
	}
	
	public BlockQueue getQueue()
	{
		return mBlockQueue;
	}
	
	

}
