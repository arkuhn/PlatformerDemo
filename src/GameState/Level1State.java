package GameState;

import Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.*;
import Entity.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private HUD hud;

	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(.15);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);

		enemies = new ArrayList<Enemy>();
		Slugger s;
		s = new Slugger (tileMap);
		s.setPosition(100, 100);
		enemies.add(s);

		explosions = new ArrayList<Explosion>();
		hud = new HUD(player);
	}
	
	
	public void update() {



		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);

		//set background
		bg.setPosition(tileMap.getx(), tileMap.gety());

		player.checkAttack(enemies);

		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}

		for(int i=0; i< explosions.size(); i++){
			Explosion e = explosions.get(i);
			e.update();

			if(e.shouldRemove()){
				explosions.remove(e);
				i--;
			}
		}
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);

		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}

		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).setMapPosition((int)tileMap.getx(),(int) tileMap.gety());
			explosions.get(i).draw(g);
		}

		hud.draw(g);
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching(true);
		if(k == KeyEvent.VK_F) player.setFiring(true);
		if(k == KeyEvent.VK_D) player.setDashing(true);
		if(k == KeyEvent.VK_H) player.setHealing(true);
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
		if(k == KeyEvent.VK_D) player.setDashing(false);
		if(k == KeyEvent.VK_R) player.setScratching(false);
		if(k == KeyEvent.VK_F) player.setFiring(false);
		if(k == KeyEvent.VK_H) player.setHealing(false);
	}
	
}












