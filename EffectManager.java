package com.example.pojodrop;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;

public class EffectManager {
	
	LinkedList<ScreenEffect> mEffects;
	GameView mGame;
	
	public EffectManager(GameView view){
		mEffects = new LinkedList<ScreenEffect>();
		mGame = view;
	}
	
	public void addEffect(ScreenEffect effect){
		effect.OnEnter(mGame);
		mEffects.add(effect);
	}
	
	
	public void removeEffect(ScreenEffect effect){
		effect.OnExit(mGame);
		mEffects.remove(effect);
	}
	
	public void updateEffects(float time)
	{
		for(int i = 0; i < mEffects.size(); i++)
		{
			ScreenEffect effect = mEffects.get(i);
			effect.Update(time);
			
			if(effect.isDone()){
				removeEffect(effect);
			}
		}
	}
	
	public void renderEffects(Canvas canvas)
	{ 
		for(int i = 0; i < mEffects.size(); i++)
			mEffects.get(i).Render(canvas);
	}

}
