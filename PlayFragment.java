package com.example.pojodrop;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayFragment extends Fragment {

	GameView mGameView;
	public PlayFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		mGameView = new GameView(parent.getContext());
		return mGameView;
	}

}
