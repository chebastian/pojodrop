package com.example.pojodrop;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PresentScoreFragment extends Fragment {

	Button mRetryButton;
	Button mViewHighscoreButton;
	PojoGame mGame;
	String TAG = "PresentScore";


	public PresentScoreFragment(PojoGame game) {
		mGame = game;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		View v = inflater.inflate(R.layout.fragment_present_score, parent, false);
		Log.d(TAG,"entered present score");

		mRetryButton = (Button)v.findViewById(R.id.retryButton);


		mRetryButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) { 
				/*while(!mGame.getView().mThread.hasExited()){
					Log.d(TAG,"Waiting to quit");
				}*/
				android.app.FragmentManager fm = getFragmentManager();
				Fragment frag = null;//fm.findFragmentById(R.id.fragmentContainer);
				if(frag == null)
				{
					frag = new PlayFragment(mGame);
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
					frag = new HighscoreFragment(mGame);
					FragmentTransaction trans = fm.beginTransaction();
					trans.replace(R.id.fragmentContainer, frag);
					trans.commit();
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
		AddScoreTask task = new AddScoreTask(mGame.getPlayerAccount().Name(),mGame.getScoreTracker().getScore());
		task.execute();
	}

}
