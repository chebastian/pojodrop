package com.example.pojodrop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Canvas;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PresentScoreState extends State {

	Paint mScoreTextPaint;
	Point mScorePos;
	Button mRetryButton;
	
	public PresentScoreState(PojoGame game) {
		super(game);
		mScoreTextPaint = new Paint();
		mScoreTextPaint.setColor(Color.WHITE);
		mScoreTextPaint.setTextSize(30.0f);
		mScorePos = new Point(20,100);
	}
	
	public void OnEnter(GameView view)
	{
		PojoGameActivity ac = (PojoGameActivity)mGame.getView().getContext();
		FragmentManager fm = ac.getFragmentManager();
		Fragment frag = null;//fm.findFragmentById(R.id.fragmentContainer);
		frag = new PresentScoreFragment(mGame);
		fm.beginTransaction().replace(R.id.fragmentContainer, frag).commit();
	} 

}
