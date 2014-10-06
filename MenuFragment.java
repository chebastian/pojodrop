package com.example.pojodrop;

import java.io.File;
import java.io.IOException;
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
import android.widget.TextView;

public class MenuFragment extends Fragment {

	Button mStartGameButton;
	Button mHighscoreButton;
	Button mChangeNameButton;
	TextView mUserNameText;

	AlertDialog.Builder mInputNameDialog;
	PojoGame mGame;
	PlayerAccountSerializer mAccountSerializer;

	public MenuFragment(PojoGame game) {
		// TODO Auto-generated constructor stub
		mGame = game;
	}
	
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstance)
	{
		View v = inflater.inflate(R.layout.activity_game_menu, parent, false);
		mAccountSerializer = new PlayerAccountSerializer(v.getContext(), mGame.getPlayerAccount(), PlayerAccountSerializer.ACCOUNT_JSON_PATH);

		mStartGameButton = (Button)v.findViewById(R.id.startGameButton);
		
		mStartGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) { 
				FragmentManager fm = getFragmentManager();
				Fragment frag = null;
				if(frag == null)
				{
					frag = new PlayFragment();
					fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();
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
					frag = new HighscoreFragment(mGame);
					FragmentTransaction trans =  fm.beginTransaction();
					trans.replace(R.id.fragmentContainer, frag).commit(); 
				}

			}
		}); 

		setupNameDialog(v); 

		mChangeNameButton = (Button)v.findViewById(R.id.changeNameButton); 
		mChangeNameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) { 
				setupNameDialog(view);
				mInputNameDialog.show();
			}
		}); 
		
		mUserNameText = (TextView)v.findViewById(R.id.userIDText); 
		mUserNameText.setText(mGame.getPlayerAccount().Name()); 

		return v;
	}
	
	protected void setupNameDialog(View v)
	{ 
		mInputNameDialog = new AlertDialog.Builder(v.getContext());
		mInputNameDialog.setTitle("Enter your name");
		mInputNameDialog.setCancelable(false);
		mInputNameDialog.setMessage("Whats your name");

		final EditText input = new EditText(v.getContext());
		mInputNameDialog.setView(input); 

		mInputNameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String res = input.getText().toString();
				res = res.replaceAll("[^a-zA-Z0-9\\s]", "");
				mGame.setplayerName(res);
				mUserNameText.setText(res);
				
				try {
					mAccountSerializer.savePlayerAccount();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		});
		
		mInputNameDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void onResume()
	{
		loadUserAccount();
		super.onResume();
	}
	
	protected void loadUserAccount()
	{
		PlayerAccount account = new PlayerAccount("");
		PlayerAccountSerializer serializer = new PlayerAccountSerializer(this.getView().getContext(),account, PlayerAccountSerializer.ACCOUNT_JSON_PATH);
		try 
		{
			account = serializer.loadPlayerAccount(mGame); 
			mUserNameText.setText(account.Name());
			mGame.setplayerName(account.Name());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
