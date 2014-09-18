package com.example.pojodrop;

import android.net.Uri;
import android.os.AsyncTask;

public class AddScoreTask extends AsyncTask<String, String, String> {

	PojoGame mGame;
	public AddScoreTask(PojoGame game)
	{
		mGame = game;
	}
	@Override
	protected String doInBackground(String... arg0) {

		ServerInterface server = new ServerInterface();
		server.addScore(mGame.playerName(), mGame.getScoreTracker().getScore());

		String url_str =  Uri.parse(server.SERVER_URL).buildUpon()
				.appendQueryParameter("command", "addNewScore")
				.appendQueryParameter("name", mGame.playerName())
				.appendQueryParameter("score", Integer.toString(mGame.getScoreTracker().getScore()))
				.build().toString(); 
		
		return server.sendClientRequest(url_str); 
	}

}
