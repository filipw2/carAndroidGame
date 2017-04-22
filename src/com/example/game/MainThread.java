package com.example.game;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
	
	private int FPS = 60;
	private double avergeFPS;
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	private boolean running;
	public static Canvas canvas;

	public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {

		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void run()
	{
		long startTime;
		long timeMillis;
		long waitTime;
		long totalTime = 0;
		int frameCount= 0;
		long targetTime = 1000/FPS;
		
		
		while(running){
			startTime = System.nanoTime();
			canvas = null;
			
			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder){
					this.gamePanel.update();
					this.gamePanel.draw(canvas);
				}
			}catch(Exception e){}
			finally{
				
				try{
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
				catch(Exception e){e.printStackTrace();}
			}
			
			timeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - timeMillis;
			
			try{
				Thread.sleep(waitTime);
			}catch(Exception e){}
			
			totalTime += System.nanoTime()-startTime;
			frameCount++;
			if(frameCount == FPS){
				avergeFPS = 1000.0/(((double)totalTime/(double)frameCount)/1000000.0);
				frameCount = 0;
				totalTime = 0;
				System.out.println(avergeFPS);
			}
			
		}
		
		
	}
	
	public void setRunning(boolean b)
	{
		running=b;
	}
}
