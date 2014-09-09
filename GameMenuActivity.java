package com.example.pojodrop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class GameMenuActivity extends Activity {

	Button mHighScoreButton;
	Button mStartGameButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
		mHighScoreButton = (Button)findViewById(R.id.highScoreButton);
		
/*		mHighScoreButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				Intent openHighscoreIntent = new Intent(GameMenuActivity.this,HighscoreActivity.class);
				startActivity(openHighscoreIntent);
			}
		});*/
		
		
		mStartGameButton = (Button)findViewById(R.id.startGameButton);
		
		mStartGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				Intent openGameIntent = new Intent(GameMenuActivity.this,PojoGameActivity.class);
				startActivity(openGameIntent);
			}
		}); 
	}
	
	protected void onResume()
	{
		super.onResume();
	}
	
	protected void onRestart()
	{
		super.onRestart();
        setContentView(R.layout.activity_game_menu);
		mStartGameButton = (Button)findViewById(R.id.startGameButton);
		
		mStartGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			
				Intent openGameIntent = new Intent(GameMenuActivity.this,PojoGameActivity.class);
				startActivity(openGameIntent);
			}
		}); 
		
	}

}
