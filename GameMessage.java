package com.example.pojodrop;

public class GameMessage {

	public int mId;
	protected int mMessageID;
	
	enum GameEvents
	{
		BLOCK_SPAWNED("BLOCK SPAWN"),
		BLOCK_START_FADE("BLOCK START FADE"),
		BLOCK_DONE_FADE("BLOCK DONE FADE"),
		COMBO_ACTIVATED("COMBO START"),
		FIELD_CLEARED("FIELD CLEARED"),
		COMBO_DONE("COMBO DONE");

        private final String mName;
        GameEvents(String name)
        {
            this.mName = name;
        }

        public String toString()
        {
            return this.mName;
        }
	}

	protected GameEvents mType;
	protected boolean mReceived;

	public GameMessage(GameEvents type) {
		// TODO Auto-generated constructor stub
		mNextValidId += 1;
		mId = mNextValidId; 
		mMessageID = 0;
		mType = type;
		mReceived = false;
	}
	
	public GameEvents Type()
	{
		return mType;
	}
	
	public int getMessage()
	{
		return mMessageID;
	}
	
	public void setReceived()
	{
		mReceived = true;
	}
	
	public boolean isReceived()
	{
		return mReceived;
	}

    public boolean equals(GameMessage msg)
    {
        return msg.getEventType() == this.getEventType();
    }

    public GameEvents getEventType()
    {
        return this.mType;
    }

    public String toString()
    {
        return this.mType.toString();
    }
	
	public static int mNextValidId = 0;

}
