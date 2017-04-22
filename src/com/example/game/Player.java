package com.example.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;

public class Player extends GameObject{

	private Bitmap playerCar;
	private boolean side; // false - left, true- right
	private boolean playing;
	//private int width;
	
	public Player(Bitmap b){
		playing= true;
		playerCar = b;
		y= 600;
		side = true;
		height = 250;
		width= 150;
		
	}
	
	public void setSide(Boolean b){side = b;}
	
	public void setWidth(int i){
		width=i;
	}
	
	public void update(){
		//System.out.println(getWidth());
		if(side){
			x= 240;
			
		}
		else{
			x= 0;
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(playerCar,(int)x,(int)y,null);
	}
	
	public void setPlaying(boolean b){playing = b;}
	
	public boolean getPlaying(){return playing;}
}
