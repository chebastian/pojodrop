package com.example.pojodrop;

public class HighscoreItem {

		String Name;
		int Score;

	public HighscoreItem(String name, int score) {
		Name = name;
		Score = score; 
	}
	
	public String toString()
	{
		return "Name: " + Name + " score: " + Score; 
	}

}
