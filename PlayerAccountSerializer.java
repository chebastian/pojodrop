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
import android.provider.ContactsContract.CommonDataKinds.Contactables;

public class PlayerAccountSerializer {

	PlayerAccount mAccount;
	String mFileDest;
	Context mContext;
	
	public static final String ACCOUNT_JSON_PATH = "accounts.json";

	public PlayerAccountSerializer(Context context, PlayerAccount player,String destination) 
	{
		mAccount = player;
		mFileDest = destination;
		mContext = context;
	}
	
	public void savePlayerAccount() throws IOException
	{
		Writer writer = null;
		try
		{
			OutputStream outStream = mContext.openFileOutput(mFileDest, mContext.MODE_PRIVATE);
			writer = new OutputStreamWriter(outStream);
			writer.write(mAccount.toJSON().toString());
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
	
	public PlayerAccount loadPlayerAccount(PojoGame game) throws IOException
	{
		PlayerAccount playerAcc = null;
		BufferedReader reader = null;
		Context context = this.mContext;
		
		try {
			InputStream inStream = context.openFileInput(this.ACCOUNT_JSON_PATH);
			reader = new BufferedReader(new InputStreamReader(inStream));
			StringBuilder jsonBuilder = new StringBuilder();
			String jsonLine = new String(); 
	
			while( (jsonLine = reader.readLine()) != null)
			{
				jsonBuilder.append(jsonLine);
			}
			
			JSONObject jsonObj = (JSONObject)new JSONTokener(jsonBuilder.toString()).nextValue();
			
			String name = jsonObj.getString(PlayerAccount.JTAG_NAME);
			String uniquieId = jsonObj.getString(PlayerAccount.JTAG_UNIQUIE_ID); 
			
			playerAcc = new PlayerAccount(name);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(reader != null)
				reader.close();
		}
		
		return playerAcc;
	}


}
