package com.example.pojodrop;

public class QuickPlayState extends State {

	PuzzleField mField;
	float mPlayTime;
	
	public QuickPlayState(GameView game) {
		super(game);
		// TODO Auto-generated constructor stub
	}
	
	public void OnEnter(GameView game)
	{
		mField = new PuzzleField(6, 12);
		mField.init(game);
		
		mPlayTime = game.getPlayTime();
	}
	
	public void Update(float time)
	{
		mField.update(time);
		updatePlayTime(time);
	}
	
	public void updatePlayTime(float time){
		mPlayTime -= time;
	}

	public void OnExit(GameView game)
	{
		
	}

}
