package com.example.pojodrop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment {

	Button mStartGameButton;

	public MenuFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		View v = inflater.inflate(R.layout.activity_game_menu, parent, false);

		mStartGameButton = (Button)v.findViewById(R.id.startGameButton);
		
		mStartGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				//Intent openGameIntent = new Intent(GameMenuActivity.this,PojoGameActivity.class);
				//startActivity(openGameIntent);
			}
		}); 

		return v;
	}

}
