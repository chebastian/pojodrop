package com.example.pojodrop;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ShakeLogger implements SensorEventListener {

	private long mNow;
	private long mLastShake;
	private long mLastUpdate;
	private long mTimeDiff;
	private long mInterval;
	
	private float mLastX, mLastY, mLastZ;
	private float mThreshold;
	
	public ShakeLogger() {
		// TODO Auto-generated constructor stub
		mLastShake = 0;
		mNow = 0;
		mLastUpdate = 0;
		mTimeDiff = 0;
		mLastX = 0;
		mLastY = 0;
		mLastZ = 0; 
		mInterval= 200;
		mThreshold = 15.0f;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
			
		mNow = event.timestamp;
		
		if(mLastUpdate == 0)	
		{
			mLastUpdate = mNow;
			mLastShake = mNow;
			mLastX = event.values[0];
			mLastY = event.values[1];
			mLastZ = event.values[2];
		}
		else
		{
			mTimeDiff = mNow - mLastUpdate;
			if(mTimeDiff > 0)
			{

				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2]; 

				float force = Math.abs(x + y + z - mLastX - mLastY - mLastZ);
				if(mTimeDiff > mInterval && Float.compare(force, mThreshold) > 0)
				{
					onShake();
					mLastShake = mNow;
				}
				mLastUpdate = mNow;
				mLastX = event.values[0];
				mLastY = event.values[1];
				mLastZ = event.values[2];
			}
		}
	}
	
	public void onShake()
	{
		
	}
}
