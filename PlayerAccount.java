package com.example.pojodrop;

public class PlayerAccount {

	String mName;
	int mUniqueID;
	
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

}
