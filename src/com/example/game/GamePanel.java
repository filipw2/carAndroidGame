package com.example.game;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 288;
	private int speed;
	private MainThread thread;
	private Background bg;
	private Player player;
	private ArrayList<Cars> cars;
	private long carStartTime;
	private long gameStart;
	private int score;

	Random rand = new Random();

	public GamePanel(Context context) {

		super(context);

		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this);

		setFocusable(true);

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.road));
		player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.car));
		cars = new ArrayList<Cars>();
		score = 0;
		speed = 10;
		carStartTime = System.nanoTime();
		gameStart = System.nanoTime();
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		boolean retry = true;

		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getX();
		float sWidth = (float) getWidth();
		//System.out.println(eventX);

		if (eventX < (sWidth / 2.0)) {
			player.setSide(false);
			return true;
		}
		if (eventX > (sWidth / 2.0)) {
			player.setSide(true);
			return true;
		}
		return super.onTouchEvent(event);
	}

	public void update() {
		if (player.getPlaying()) {
			bg.update(speed);
			player.update();
			
			long carElapsed = (System.nanoTime() - carStartTime) / 1000000;
			long gStart= (System.nanoTime() - gameStart) / 1000000;
			if (carElapsed > (24000 + rand.nextInt(2000)) * ((double) 1 / (speed * 1.3)))  {


				cars.add(new Cars(BitmapFactory.decodeResource(getResources(), R.drawable.car), rand.nextBoolean()));

				// reset timer
				carStartTime = System.nanoTime();
			}
			if(gStart>3000){
				speed++;				
				gameStart = System.nanoTime();
			}
			// loop through every car and check collision and remove
			for (int i = 0; i < cars.size(); i++) {
				// update car
				cars.get(i).update(speed);

				if (collision(cars.get(i), player)) {
					//cars.remove(i);
					thread.setRunning(false);
					break;
				}

				// remove car if it is way off the screen
				if (cars.get(i).getY() > getHeight() + 200) {
					cars.remove(i);
					score++;
					break;
				}
			}
			
		}

	}

	public boolean collision(GameObject a, GameObject b) {
		if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		// System.out.println(getWidth());
		final float scaleFactorX = getWidth() / (float) WIDTH;
		final float scaleFactorY = getHeight() / (float) HEIGHT;
		if (canvas != null) {
			final int savedState = canvas.save();

			canvas.scale(scaleFactorX, scaleFactorY);
			bg.draw(canvas);
			canvas.restoreToCount(savedState);
			canvas.scale(scaleFactorX, scaleFactorX);
			player.draw(canvas);
			for (Cars car : cars) {
				car.draw(canvas);
			}
			canvas.restoreToCount(savedState);
			drawText(canvas);
		}
	}
	
	public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("SCORE: " + score*speed + " SPEED: " + speed, 10, HEIGHT - 100, paint);
    }
}
