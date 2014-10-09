package com.example.pojodrop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;

public class PlayerHighscoreSerializer {

	String mDst;
	Context mContext;
	final String JTAG_SCORE = "score";
	public PlayerHighscoreSerializer(Context context, String dst) {
		// TODO Auto-generated constructor stub
		mDst = dst;
		mContext = context;
	}
	
	public void saveHighscore(PlayerAccount accout, int score) throws IOException
	{
		Writer writer = null;
		try
		{
			OutputStream outStream = mContext.openFileOutput(mDst, mContext.MODE_PRIVATE);
			writer = new OutputStreamWriter(outStream);
			JSONObject json = accout.toJSON();
			json.put(JTAG_SCORE, score);
			writer.write(json.toString());
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally{
			if(writer != null)
				writer.close();
		}
	}
	
	public int getSavedScore()
	{
		BufferedReader reader = null;
		int score = 0;
		try
		{
			InputStream inStream = mContext.openFileInput(this.mDst);
			reader = new BufferedReader(new InputStreamReader(inStream));
			StringBuilder jsonBuilder = new StringBuilder();
			String jsonLine = new String(); 
	
			while( (jsonLine = reader.readLine()) != null)
			{
				jsonBuilder.append(jsonLine);
			}
			
			JSONObject jsonObj = (JSONObject)new JSONTokener(jsonBuilder.toString()).nextValue();
			
			String name = jsonObj.getString(PlayerAccount.JTAG_NAME);
			score = jsonObj.getInt(JTAG_SCORE);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{ 
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return score;
	}

}
