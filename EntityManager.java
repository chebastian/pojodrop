package com.example.pojodrop;

import java.util.ArrayList;

import android.graphics.Canvas;

public class EntityManager {
	
	ArrayList<RenderableEntity> mEntitys;
	public  EntityManager(){
		mEntitys = new ArrayList<RenderableEntity>(); 
	}
	
	public void addEntity(RenderableEntity ent){
		mEntitys.add(ent);
	}
	
	public void removeEntity(RenderableEntity ent){
		mEntitys.remove(ent);
	}
	
	public void updateEntitys(float time){
		for(int i = 0; i < mEntitys.size(); i++){
			mEntitys.get(i).update(time);
		}
	}

	public void renderEntitys(Canvas g){
		for(int i = 0; i < mEntitys.size(); i++){
			mEntitys.get(i).render(g);
		}
	} 
	
	public void cleanDeadEntitys(){
		for(int i = 0; i < mEntitys.size(); i++){
			if(!mEntitys.get(i).isAlive())
				mEntitys.remove(i);
		} 
	}
}
