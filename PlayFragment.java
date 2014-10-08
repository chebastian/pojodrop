package com.example.pojodrop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayFragment extends Fragment {

	GameView mGameView;
	PojoGame mGame;
	public PlayFragment(PojoGame game) {
		mGame = game;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		mGameView = new GameView((PojoGameActivity)parent.getContext());
		return mGameView; 
	}
	
	public void onDestroy()
	{
		///mGameView.mThread.setRunnint(false);
		mGame.stopGame();
		super.onDestroy();
	}
	
	public void onPause()
	{
		super.onPause();
	}
	
	public void onStop()
	{
//		mGameView.mThread.setRunnint(false);
		super.onStop();
	}
	
	public void onResume()
	{
		mGame.startGame(mGameView);
		super.onResume();
	} 
}
