package com.example.pojodrop;

import android.net.Uri;
import android.os.AsyncTask;

public class AddScoreTask extends AsyncTask<String, String, String> {

	String mNameToAdd;
	int mScoreToAdd;
	public AddScoreTask(String name, int score)
	{
		mNameToAdd = name;
		mScoreToAdd = score;
	}
	@Override
	protected String doInBackground(String... arg0) {

		ServerInterface server = new ServerInterface();
		//server.addScore(mGame.playerName(), mGame.getScoreTracker().getScore());

		String url_str =  Uri.parse(server.SERVER_URL).buildUpon()
				.appendQueryParameter("command", "addNewScore")
				.appendQueryParameter("name", mNameToAdd)
				.appendQueryParameter("score", Integer.toString(mScoreToAdd))
				.build().toString(); 
		
		String result = server.sendClientRequest(url_str);
		
		return result; 
	}

}
