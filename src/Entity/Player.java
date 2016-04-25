package Entity;

import TileMap.*;
import com.sun.deploy.net.protocol.chrome.ChromeURLConnection;
import sun.awt.EventListenerAggregate;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Currency;
import java.util.FormatFlagsConversionMismatchException;

public class Player extends MapObject {
	
	// player stuff
	private long health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	private boolean canDash;
	private boolean canHeal;
	private static final int DASH_COOLDOWN = 500;
	private static final int DASH_DELAY = 100;
	private static final int HEAL_COOLDOWN = 5000;
	private static final int HEAL_DELAY = 3000;
	private static final int HEAL_INT = 500;



	// fireball
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	// scratch
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	// gliding
	private boolean gliding;
	private boolean dashing;
	private boolean healing;
	
	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 8, 1, 2, 4, 2, 5, 4, 6
	};

	private class DashThread extends Thread{
		@Override
		public void run() {
			canDash = false;
			try {
				sleep(DASH_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			setDashing(false);
			try {
				sleep(DASH_COOLDOWN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			canDash = true;
		}
	}

	private class HealThread extends Thread{
		@Override
		public void run() {
			canHeal = false;
			try {
				sleep(HEAL_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			for (int i = 0; i <= 3; i++){
				try {
					sleep(HEAL_INT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (health < maxHealth){
					health += 1;
				}
			}

			setHealing(false);
			try {
				sleep(HEAL_COOLDOWN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			canHeal = true;
		}
	}

	// animation actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int GLIDING = 4;
	private static final int FIREBALL = 5;
	private static final int SCRATCHING = 6;
	private static final int DASHING = 7;
	private static final int HEALING = 8;
	
	public Player(TileMap tm) {
		
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;

		maxHealth = 10;
		health = 10;
		fire = maxFire = 2500;
		
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		
		scratchDamage = 8;
		scratchRange = 40;

		canDash = true;
		canHeal = true;
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites2.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 9; i++) {
				
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i == 6) {
						bi[j] = spritesheet.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}
					else if(i == 8){
						bi[j] = spritesheet.getSubimage(
								j * 40,
								i * height,
								40,
								height
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
	}
	
	public long getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }

	public void setFiring(boolean b) {
		firing = b;
	}
	public void setScratching(boolean b) {
		scratching = b;
	}
	public void setGliding(boolean b) { 
		gliding = b;
	}
	public void setDashing(boolean b) {
		if(b && canDash){
			dashing = b;
			DashThread t = new DashThread();
			t.start();
		}
		else if(!b){
			dashing = b;
		}
	}
	public void setHealing(boolean b){
		if(b && canHeal){
			healing = b;
			HealThread d = new HealThread();
			d.start();
		}
		else if(!b){
			healing = b;
		}
	}
	/*
	public void checkConsume(ArrayList<Enemy> enemies){
		for(int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			if (e.isItem()){
				if(e.getx() == x && e.gety() == y && consuming){
					setHealing(true);
					enemies.remove(e);
				}
			}
		}
	}
	*/

	public void checkAttack(ArrayList<Enemy> enemies){

		// loop through enemies
		for(int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// scratch attack
			if(scratching) {
				if(facingRight) {
					if(
							e.getx() > x &&
									e.getx() < x + scratchRange &&
									e.gety() > y - height / 2 &&
									e.gety() < y + height / 2
							) {
						e.hit(scratchDamage);
					}
				}
				else {
					if(
							e.getx() < x &&
									e.getx() > x - scratchRange &&
									e.gety() > y - height / 2 &&
									e.gety() < y + height / 2
							) {
						e.hit(scratchDamage);
					}
				}
			}

			// fireballs
			for(int j = 0; j < fireBalls.size(); j++) {
				if(fireBalls.get(j).intersects(e)) {
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
					break;
				}
			}

			// check enemy collision
			if(intersects(e)) {
				if(!e.isItem()){
					hit(e.getDamage());
				}
				else{
					enemies.remove(e);
					if(maxHealth > health){
						setHealing(true);
					}
				}
			}

		}
	}

	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		if( health < 0) health = 0;
		if (health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {

			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if(
		(currentAction == SCRATCHING || currentAction == FIREBALL) &&
		!(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}

		if(dashing){
			dy += fallSpeed * 4;

			if(right) {
				dx += moveSpeed * 30;

			}
			if(left) {
				dx -= moveSpeed * 30;
			}
		}


		// falling
		if(falling) {


			if(dy > 0 && gliding) dy += fallSpeed * 0.1;
			else dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		
	}
	
	public void update() {
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);



		//check attack stopped
		if(currentAction == SCRATCHING){
			if (animation.hasPlayedOnce()) scratching = false;

		}
		if(currentAction == FIREBALL){
			if(animation.hasPlayedOnce() ) firing = false;
		}

		fire += 1;

		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL){
			if (fire > fireCost){
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}



		for(int i = 0; i < fireBalls.size(); i++){
			fireBalls.get(i).update();
			if (fireBalls.get(i).shouldRemove()) {

				fireBalls.remove(i);
				i--;
			}
		}

		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000){
				flinching = false;
			}
		}
		// set animation
		if(scratching) {
			if(currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 60;
			}
		}

		else if(healing){
			if(currentAction != HEALING){
				currentAction = HEALING;
				animation.setFrames(sprites.get(HEALING));
				animation.setDelay(300);
				width = 40;
			}
		}
		else if(dashing){
			if(currentAction != DASHING){
				currentAction = DASHING;
				animation.setFrames(sprites.get(DASHING));
				animation.setDelay(100);
				width= 30;

			}

		}
		else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy > 0) {

			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			}
			else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}
		
		animation.update();
		
		// set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();

		for(int i = 0; i < fireBalls.size(); i++){
			fireBalls.get(i).draw(g);
		}

		// draw player
		if(flinching) {
			long elapsed =
				(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}

		super.draw(g);
		

		
	}
	
}

















