package com.example.pojodrop;

import java.util.LinkedList;
import java.util.List;

public class EffectManager {
	
	LinkedList<ScreenEffect> mEffects;
	public EffectManager(GameView view){
		mEffects = new LinkedList<ScreenEffect>();
	}
	
	public void addEffect(ScreenEffect effect){
		mEffects.add(effect);
	}
	
	
	public void removeEffect(ScreenEffect effect){
		mEffects.remove(effect);
	}

}
