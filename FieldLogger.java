package com.example.pojodrop;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class FieldLogger {

	ArrayList<String> mMessageLog;
	ArrayList<String> mFieldSnaps;
	int SNAPS_MAX_SIZE = 10;
	public FieldLogger() {
		// TODO Auto-generated constructor stub
		mMessageLog = new ArrayList<String>();
		mFieldSnaps = new ArrayList<String>();
	}
	
	public void addMessage(String msg)
	{
		String res = "";
		Date d = new Date();
		res += "time: " + d.getTime() + " | ";
		res += msg + " | ";
		mMessageLog.add(res); 
		
		Log.d("",res);
	}
	
	public void saveSnapshotOfField(PuzzleField field)
	{
		String row = "\n";
		for(int i = 0; i < field.FIELD_HEIGHT; i++)
		{
			for(int j = 0; j < field.FIELD_WIDTH; j++)
			{
				if(!field.IsBlock(j, i))
				{
					row += "[ ]";
					continue;
				}
				PuzzleBlock b = field.GetBlock(j, i);
				row += b.toString();
			}
			row += "\n";
		}
		
		row += "----------------------------------------------------";
		
		mFieldSnaps.add(row);

		if(mFieldSnaps.size() > SNAPS_MAX_SIZE)
		{
			mFieldSnaps.remove(0);
		}
	}
	
	public void sendLogToServer(String surl)
	{
		ServerInterface server = new ServerInterface();
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		for(int i = 0; i < mFieldSnaps.size(); i++)
		{
			params.add(new BasicNameValuePair("snap",mFieldSnaps.get(i)));
		}
		server.executeRequest("http://sebastianferngren.com/log.php", params);
		
	}
	
	
	

}
