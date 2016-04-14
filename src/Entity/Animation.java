package Entity;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;

	//Timer between frames
	private long startTime;
	//Time between frames
	private long delay;

	//Has the animation played already? Ex: Attack animations happen once
	private boolean playedOnce;


	public void Animation() {

		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) {
		delay = d;
	}

	public void setFrame(int i) {
		currentFrame = i;
	}

	//Should we move to next frame
	public void update() {

		//No animation
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;

		if(elapsed > delay) {
			currentFrame++; //Move onto next frame
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
		
	}
	
	public int getFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	
}
















