package com.example.pojodrop;

import android.os.AsyncTask;
import android.util.Log;

public class GetServerListTask extends AsyncTask<String, String, String>{

	public GetServerListTask() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doInBackground(String... arg0) {
		ServerInterface server = new ServerInterface();
		
		String list = server.getServerList();
		return list;
	}
	
	protected void onPostExecute(String object)
	{
		Log.d("", object);
	}

}
