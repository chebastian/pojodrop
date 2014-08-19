package com.example.pojodrop;


public class ScoreTracker {

	int mScore;
	int mComboCounter;
	
	public ScoreTracker()
	{
		mScore = 0;
		mComboCounter = 0;
	}

	
	public void increaseComboCounter()
	{
		mComboCounter += 1;
	}
	
	public void clearComboCounter()
	{
		mComboCounter = 0;
	}
	
	public int getComboCounter()
	{
		return mComboCounter;
	}

	public void increaseScore(int amount)
	{
		mScore += amount;
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
	
	

}
