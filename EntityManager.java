package com.example.pojodrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.graphics.Canvas;

public class EntityManager {
	
	ArrayList<RenderableEntity> mEntitys;
	boolean mNeedsToUpdate;
	
	public  EntityManager(){
		mEntitys = new ArrayList<RenderableEntity>(); 
	}
	
	public RenderableEntity getEntityById(int ID)
	{
		for(RenderableEntity ent : mEntitys)
		{
			if(ent.entityID() == ID)
			{
				return ent;
			}
		}
		
		return null;
	}
	
	public void addEntity(RenderableEntity ent){
		mEntitys.add(ent);
		mNeedsToUpdate = true;
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
		if(needsToBeSorted())
			Collections.sort(mEntitys);
		
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
	
	public boolean needsToBeSorted()
	{ 
		return mNeedsToUpdate;
	}
}
