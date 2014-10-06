package com.example.pojodrop;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerAccount {

	String mName;
	int mUniqueID;
	public static final String JTAG_NAME = "Name";
	public static final String JTAG_UNIQUIE_ID = "uniquieIdentifier";

	public PlayerAccount(String name) {
		mName = name;
		mUniqueID = 0; 
	}
	
	public PlayerAccount(String filename, PojoGame game)
	{
		//TODO add parsing of account JSONFile to load player information
	}
	
	public String Name()
	{
		return mName;
	}
	
	public void setName(String name)
	{
		mName = name;
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JTAG_NAME, Name());
		json.put(JTAG_UNIQUIE_ID, mUniqueID);
		
		return json;
	}

}
