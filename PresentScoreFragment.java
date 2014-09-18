package com.example.pojodrop;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PresentScoreFragment extends Fragment {

	Button mRetryButton;
	Button mViewHighscoreButton;
	PojoGame mGame;

	public PresentScoreFragment(PojoGame game) {
		// TODO Auto-generated constructor stub
		mGame = game;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		//super.onCreateView(inflater, parent, savedInstance);
		View v = inflater.inflate(R.layout.fragment_present_score, parent, false);

		mRetryButton = (Button)v.findViewById(R.id.retryButton);

		mRetryButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) { 
				android.app.FragmentManager fm = getFragmentManager();
				Fragment frag = null;//fm.findFragmentById(R.id.fragmentContainer);
				if(frag == null)
				{
					frag = new PlayFragment();
					fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();
				} 
			}
		}); 
		
		mViewHighscoreButton = (Button)v.findViewById(R.id.highScoreButton2);
		mViewHighscoreButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) { 
				android.app.FragmentManager fm = getFragmentManager();
				Fragment frag = null;//fm.findFragmentById(R.id.fragmentContainer);
				if(frag == null)
				{
					frag = new HighscoreFragment();
					FragmentTransaction trans = fm.beginTransaction();
					trans.replace(R.id.fragmentContainer, frag);
					trans.addToBackStack(null);
					trans.commit();
					//fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();
				} 
			}
		}); 

		
		TextView text = (TextView)v.findViewById(R.id.player_score);
		text.append(" " + Integer.toString(mGame.getScoreTracker().getScore()));
		
		sendScoreToServer();
		return v;
	}
	
	public void sendScoreToServer()
	{
		AddScoreTask task = new AddScoreTask(mGame);
		task.execute();
	}

}
