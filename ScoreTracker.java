package com.example.pojodrop;

import android.graphics.Point;


public class ScoreTracker {

	int mScore;
	int mComboCounter;
	PojoGame mGame;
	
	public ScoreTracker(PojoGame game)
	{
		mScore = 0;
		mComboCounter = 0;
		mGame = game;
	}

	
	public void increaseComboCounter()
	{
		mComboCounter += 1;
	}
	
	public void clearComboCounter()
	{
		mComboCounter = 0;
	}

	public int increaseScore(int amount)
	{
	   	mScore += amount * mComboCounter;
	   	return amount * mComboCounter;
	}
	
	public int getComboCounter(){
		return mComboCounter;
	}
	
	public void increaseScore(int clusterSize, int chain)
	{
		int sc = clusterSize * 100;
		sc += sc * (chain/2);
		mScore += sc;
	}
	
	public int getScore()
	{
		return mScore;
	}
	
	public void reset()
	{
		mScore = 0;
		mComboCounter = 0;
	}
	
	

}
