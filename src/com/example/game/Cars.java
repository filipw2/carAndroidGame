package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cars extends GameObject{
	
	private Bitmap car;
	
	private long startingTime;
	
	public Cars(Bitmap res, Boolean side){
		y=-300;
		width=150;
		height=250;
		
		System.out.println(side);
		if(side){
			x= 240;
			
		}
		else{
			x= 0;
		}
		
		car=res;
	}
	
	public void update(int speed)
    {
		y+=speed;       
       //System.out.println(speed);
    }
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(car,x,y,null);
        }catch(Exception e){}
    }
 
    @Override
    public int getWidth()
    {
        //offset slightly for more realistic collision detection
        return width-10;
    }
}
