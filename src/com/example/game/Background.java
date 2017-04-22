package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	
	
	private Bitmap image;
	private int x, y;
	
	
	public Background(Bitmap res)
	{
		
		image = res;
	}
	
	public void update(int speed)
	{
		y+=speed/2;
		if(y>=GamePanel.HEIGHT){
			y=0;
		}
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(image,  x, y, null);
		if(y>0)
		{
			canvas.drawBitmap(image ,  x, y-GamePanel.HEIGHT, null );
		}
	}
	
	

}
