package com.example.pojodrop;

import java.util.ArrayList;

public class GameMessageManager {

	protected ArrayList<GameMessage> mEvents;
	protected ArrayList<GameMessageListener> mListeners;
	
	public GameMessageManager() {
		// TODO Auto-generated constructor stub
		mEvents = new ArrayList<GameMessage>();
		mListeners = new ArrayList<GameMessageListener>();
	}

	public void update()
	{
		sendMessagesToListeners();
		removeReceivedMessages(); 
	}
	
	public void sendMessagesToListeners()
	{
		for(int i = 0; i < mEvents.size(); i++)
		{
			for(int j = 0; j < mListeners.size(); j++)
			{
				GameMessageListener listener = mListeners.get(j);
				listener.onMessage(mEvents.get(i));
			} 
			
			mEvents.get(i).setReceived();
		}
	}
	
	public void addEvent(GameMessage evt)
	{
		mEvents.add(evt);
	}
	
	public void addListener(GameMessageListener listener)
	{
		mListeners.add(listener);
	}
	
	public void removeReceivedMessages()
	{
		for(int i = 0; i < mEvents.size(); i++)
		{
			GameMessage evt = mEvents.get(i);
			if(evt.isReceived())
				mEvents.remove(i);
		}
	}

}
