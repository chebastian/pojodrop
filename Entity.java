package com.example.pojodrop;


public class Entity {

	int EntityID;
	boolean Alive;
	public Entity(int id)
	{
		EntityID = id;
		Alive = true;
	}
	
	public void update(float time)
	{
		
	}
	
	public void Kill()
	{
		Alive = false;
	}
	
	
}
