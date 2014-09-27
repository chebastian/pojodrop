package com.example.pojodrop;

import java.security.spec.MGF1ParameterSpec;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MenuFragment extends Fragment {

	Button mStartGameButton;
	Button mHighscoreButton;
	AlertDialog.Builder mInputNameDialog;

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
				mInputNameDialog.show();
				FragmentManager fm = getFragmentManager();
				Fragment frag = null;
				if(frag == null)
				{
					frag = new PlayFragment();
					fm.beginTransaction().add(R.id.fragmentContainer, frag).commit();
				}

			}
		}); 

		mHighscoreButton = (Button)v.findViewById(R.id.highScoreButton);
		
		mHighscoreButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) { 
				FragmentManager fm = getFragmentManager();
				Fragment frag = null;
				if(frag == null)
				{
					frag = new HighscoreFragment();
					FragmentTransaction trans =  fm.beginTransaction();
					trans.addToBackStack(null);
					trans.replace(R.id.fragmentContainer, frag).commit(); 
				}

			}
		}); 
			
		mInputNameDialog = new AlertDialog.Builder(v.getContext());
		mInputNameDialog.setTitle("Enter your name");
		mInputNameDialog.setMessage("Whats your name");

		final EditText input = new EditText(v.getContext());
		mInputNameDialog.setView(input);

		mInputNameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
			} 
		});
		
		mInputNameDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});

		return v;
	}

}
