package com.example.pojodrop;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;

public class GetServerListTask extends AsyncTask<String, String, String>{
	
	String returnString;
	boolean mIsReleased;
	ArrayList<HighscoreItem> mHighscore;

	public GetServerListTask(String toReturn) {

		returnString = toReturn;
		mIsReleased = false;
		mHighscore = new ArrayList<HighscoreItem>();
	}

	@Override
	protected String doInBackground(String... arg0) {
		ServerInterface server = new ServerInterface();
		
		String list = "nothing";
		try {
			list = server.getList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String list = "";
		//list = server.executeHttpRequest(server.SERVER_URL, "command=getList");
		//String list ="";
		//server.addScore("QQSefe", 998);
		return list;
	}
	
	protected void onPostExecute(String object)
	{
		mHighscore = parseXmlFromString(object);
		myPostExecute(object);
		returnString = object;
		Log.d("", object);
	}
	
	public void myPostExecute(String res)
	{
	}
	
	public void release()
	{
		mIsReleased = true;
	}
	
	public ArrayList<HighscoreItem> parseXmlFromString(String xmlString)
	{
		ArrayList<HighscoreItem> items = new ArrayList<HighscoreItem>();
		XmlPullParserFactory factory;

		String ITEM_TAG = "HighscoreItem";
		String SCORE_TAG = "Score";
		String NAME_TAG = "Name";
		
		try 
		{
			factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			parser.setInput(new StringReader(xmlString));
			
			int eventType = parser.next();
			String name = "";
			String score = "";
			String text = "";

			while(eventType != XmlPullParser.END_DOCUMENT)
			{
				if(eventType == XmlPullParser.TEXT)
					text = parser.getText();

				if(eventType == XmlPullParser.END_TAG &&
						parser.getName().equals(SCORE_TAG))
				{
					score = text;
				}
				if(eventType == XmlPullParser.END_TAG &&
						parser.getName().equals(NAME_TAG))
				{
					name = text;
				}

				if(eventType == XmlPullParser.END_TAG &&
						parser.getName().equals(ITEM_TAG))
				{
					int sc = Integer.parseInt(score);
					HighscoreItem it = new HighscoreItem(name, sc);
					items.add(it);
				}
				
				eventType = parser.next();
			}
		}
		catch (XmlPullParserException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return items;
	}
	
	

}
