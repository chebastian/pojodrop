package com.example.pojodrop;

import java.security.spec.MGF1ParameterSpec;
import java.util.concurrent.ExecutionException;

import android.app.Fragment;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class HighscoreFragment extends Fragment {

	Button mBackButton;
	TextView mServerResponse;
	String serverString;
	public HighscoreFragment() {
		serverString = "no response...";
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		//super.onCreateView(inflater, parent, savedInstance);
		View v = inflater.inflate(R.layout.activity_highscore, parent, false);

		mBackButton = (Button)v.findViewById(R.id.backButton);
		mServerResponse = (TextView)v.findViewById(R.id.server_text);
		mServerResponse.setText(serverString);

		mBackButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) { 
				android.app.FragmentManager fm = getFragmentManager();
				Fragment frag = null;//fm.findFragmentById(R.id.fragmentContainer);
				if(frag == null)
				{
					frag = new MenuFragment();
					fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();
				} 
			}
		}); 

		return v;
	}
	
	public void onResume()
	{
		super.onResume();
		GetServerListTask lst = new GetServerListTask(serverString)
		{
			public void myPostExecute(String res)
			{
				//mServerResponse.setText(res);
				ArrayAdapter<HighscoreItem> items = new ArrayAdapter<HighscoreItem>(getActivity(), R.layout.simple_list_item_1,
						mHighscore);
				
				ListView view = (ListView)getView().findViewById(R.id.highscorelist);
				view.setAdapter(items);
			}
		};
		lst.execute("");
		
		serverString = serverString;
	}

}
