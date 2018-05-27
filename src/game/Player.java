package game;

import java.awt.event.KeyEvent;

import rendering.Vector3;

public class Player {
	//Movements
	private final double WALKING_SPEED = 4.35;
	private final double GRAVITY = -32;
	private final double JUMP = 8.5;//8.94069;
	private final double PLAYER_PADDING = 0.2; //Between 0 and 0.5
	private Vector3 pos;
	private Vector3 velocity;
	private boolean w, a, s, d;
	private enum Directions {E, SE, S, SW, W, NW, N, NE};
	
	//Focus
	private final double PLAYER_HEIGHT = 1.6;
	private Vector3 focus;
	private double focusAngleH, focusAngleV;
	
	public Player(Vector3 pos) {
		this.pos = pos.copy();
		velocity = new Vector3();
		focus = Vector3.sum(getHeadPos(), new Vector3(0, 0, 1));
		focusAngleH = 0;
		focusAngleV = 0;
		updateFocusAngles();
		
	}
	
	public void tick() {
		if(onGround())
			velocity.y = Math.max(velocity.y, 0);
		else
			velocity.y += GRAVITY * Game.TICK_TIME;

		double y = pos.y + velocity.y * Game.TICK_TIME + 0.1;
		Game game = Game.getInstance();
		if (game.isObstructed(new Vector3(pos.x, y, pos.z)) ||
			game.isObstructed(new Vector3(pos.x, y + PLAYER_HEIGHT, pos.z))) {
			velocity.y = 0;
			pos.y = Math.floor(pos.y);
		}
		
		boolean blocked[] = new boolean[8];
		blocked[Directions.E.ordinal()] = game.isObstructed(new Vector3(pos.x + 1, pos.y, pos.z)) || game.isObstructed(new Vector3(pos.x + 1, pos.y + PLAYER_HEIGHT, pos.z));
		blocked[Directions.SE.ordinal()] = game.isObstructed(new Vector3(pos.x + 1, pos.y, pos.z + 1)) || game.isObstructed(new Vector3(pos.x + 1, pos.y + PLAYER_HEIGHT, pos.z + 1));
		blocked[Directions.S.ordinal()] = game.isObstructed(new Vector3(pos.x, pos.y, pos.z + 1)) || game.isObstructed(new Vector3(pos.x, pos.y + PLAYER_HEIGHT, pos.z + 1));
		blocked[Directions.SW.ordinal()] = game.isObstructed(new Vector3(pos.x - 1, pos.y, pos.z + 1)) || game.isObstructed(new Vector3(pos.x - 1, pos.y + PLAYER_HEIGHT, pos.z + 1));
		blocked[Directions.W.ordinal()] = game.isObstructed(new Vector3(pos.x - 1, pos.y, pos.z)) || game.isObstructed(new Vector3(pos.x - 1, pos.y + PLAYER_HEIGHT, pos.z));
		blocked[Directions.NW.ordinal()] = game.isObstructed(new Vector3(pos.x - 1, pos.y, pos.z - 1)) || game.isObstructed(new Vector3(pos.x - 1, pos.y + PLAYER_HEIGHT, pos.z - 1));
		blocked[Directions.N.ordinal()] = game.isObstructed(new Vector3(pos.x, pos.y, pos.z - 1)) || game.isObstructed(new Vector3(pos.x, pos.y + PLAYER_HEIGHT, pos.z - 1));
		blocked[Directions.NE.ordinal()] = game.isObstructed(new Vector3(pos.x + 1, pos.y, pos.z - 1)) || game.isObstructed(new Vector3(pos.x + 1, pos.y + PLAYER_HEIGHT, pos.z - 1));
		Vector3 displacement = velocity.copy();
		displacement.scale(Game.TICK_TIME);

		double tx = 0, tz = 0; // Ticks needed to move one block on the x/z axis
		if (displacement.x > 0)
			tx = (1 - (pos.x % 1)) / displacement.x;
		else
			tx = -pos.x / displacement.x;
		
		if (displacement.z > 0)
			tz = (1 - (pos.z % 1)) / displacement.z;
		else
			tz = -pos.z / displacement.z;
		
		Vector3 newPos = Vector3.sum(pos, displacement);
		int dx = (int)Math.floor(newPos.x) - (int)Math.floor(pos.x);
		int dz = (int)Math.floor(newPos.z) - (int)Math.floor(pos.z);
		
		if (tx > tz) {
			if (dx > 0 && blocked[Directions.E.ordinal()]) {
				pos.x = Math.floor(pos.x) + 1 - PLAYER_PADDING;
				dx = 0;
			} else if (dx < 0 && blocked[Directions.W.ordinal()]) {
				pos.x = Math.floor(pos.x) + PLAYER_PADDING;
				dx = 0;
			} else {
				pos.x += displacement.x;
			}
			displacement.x = 0;
			
			if (dx > 0) {
				blocked[Directions.N.ordinal()] = blocked[Directions.NE.ordinal()];
				blocked[Directions.S.ordinal()] = blocked[Directions.SE.ordinal()];
			} else if (dx < 0) {
				blocked[Directions.N.ordinal()] = blocked[Directions.NW.ordinal()];
				blocked[Directions.S.ordinal()] = blocked[Directions.SW.ordinal()];
			}
			
			if (dz > 0 && blocked[Directions.S.ordinal()]) {
				pos.z = Math.floor(pos.z) + 1 - PLAYER_PADDING;
				dz = 0;
			} else if (dz < 0 && blocked[Directions.N.ordinal()]) {
				pos.z = Math.floor(pos.z) + PLAYER_PADDING;
				dz = 0;
			} else {
				pos.z += displacement.z;
			}
			displacement.z = 0;
			
		} else {
			if (dz > 0 && blocked[Directions.S.ordinal()]) {
				pos.z = Math.floor(pos.z) + 1 - PLAYER_PADDING;
				dz = 0;
			} else if (dz < 0 && blocked[Directions.N.ordinal()]) {
				pos.z = Math.floor(pos.z) + PLAYER_PADDING;
				dz = 0;
			} else {
				pos.z += displacement.z;
			}
			displacement.z = 0;
			
			if (dz > 0) {
				blocked[Directions.E.ordinal()] = blocked[Directions.SE.ordinal()];
				blocked[Directions.W.ordinal()] = blocked[Directions.SW.ordinal()];
			} else if (dz < 0) {
				blocked[Directions.E.ordinal()] = blocked[Directions.NE.ordinal()];
				blocked[Directions.W.ordinal()] = blocked[Directions.NW.ordinal()];
			}
			
			if (dx > 0 && blocked[Directions.E.ordinal()]) {
				pos.x = Math.floor(pos.x) + 1 - PLAYER_PADDING;
				dx = 0;
			} else if (dx < 0 && blocked[Directions.W.ordinal()]) {
				pos.x = Math.floor(pos.x) + PLAYER_PADDING;
				dx = 0;
			} else {
				pos.x += displacement.x;
			}
			displacement.x = 0;
		}
		
		pos.add(displacement);
		
		if ((pos.x > 0 ? pos.x % 1 : 1 + pos.x % 1) > (1 - PLAYER_PADDING) && (game.isObstructed(new Vector3(pos.x + 1, pos.y + 0.1, pos.z)) ||
				  															   game.isObstructed(new Vector3(pos.x + 1, pos.y + PLAYER_HEIGHT, pos.z)))) {
			pos.x = Math.floor(pos.x) + 1 - PLAYER_PADDING;
		}
		if ((pos.x > 0 ? pos.x % 1 : 1 + pos.x % 1) < PLAYER_PADDING && (game.isObstructed(new Vector3(pos.x - 1, pos.y + 0.1, pos.z)) ||
																		 game.isObstructed(new Vector3(pos.x - 1, pos.y + PLAYER_HEIGHT, pos.z)))) {
			pos.x = Math.floor(pos.x) + PLAYER_PADDING;
		}
		if ((pos.z > 0 ? pos.z % 1 : 1 + pos.z % 1) > (1 - PLAYER_PADDING) && (game.isObstructed(new Vector3(pos.x, pos.y + 0.1, pos.z + 1)) ||
				  															   game.isObstructed(new Vector3(pos.x, pos.y + PLAYER_HEIGHT, pos.z + 1)))) {
			pos.z = Math.floor(pos.z) + 1 - PLAYER_PADDING;
		}
		if ((pos.z > 0 ? pos.z % 1 : 1 + pos.z % 1) < PLAYER_PADDING && (game.isObstructed(new Vector3(pos.x, pos.y + 0.1, pos.z - 1)) ||
																		 game.isObstructed(new Vector3(pos.x, pos.y + PLAYER_HEIGHT, pos.z - 1)))) {
			pos.z = Math.floor(pos.z) + PLAYER_PADDING;
		}
		
	}
	
	private void updateFocusAngles() {
		Vector3 cameraLookInverse = Vector3.difference(getHeadPos(), focus);
		
		if (cameraLookInverse.x != 0) {
			focusAngleH = Math.atan(Math.abs(cameraLookInverse.z)/Math.abs(cameraLookInverse.x));
			if (cameraLookInverse.x > 0) {
				if (cameraLookInverse.z < 0)
					focusAngleH = 2*Math.PI - focusAngleH;
			} else {
				if (cameraLookInverse.z > 0)
					focusAngleH = Math.PI - focusAngleH;
				else
					focusAngleH = Math.PI + focusAngleH;
			}
		} else {
			if (cameraLookInverse.z > 0)
				focusAngleH = Math.PI / 2;
			else
				focusAngleH = Math.PI * 3 / 2;
		}
		
		focusAngleV = Math.asin(cameraLookInverse.y);
	}
	
	private void updateFocusPos() {
		Vector3 head = getHeadPos();
		focus.x = head.x + Math.cos(focusAngleH) * Math.cos(focusAngleV);
		focus.y = head.y + Math.sin(focusAngleV);
		focus.z = head.z + Math.sin(focusAngleH) * Math.cos(focusAngleV);
	}
	
	public void mouseMoved(int dx, int dy) {		
		focusAngleH += dx / 250.0;
		focusAngleH %= Math.PI * 2;

		focusAngleV -= dy / 250.0;
		if (focusAngleV <= -Math.PI / 2)
			focusAngleV = Math.max(-Math.PI / 2 + 0.0005, focusAngleV);
		if (focusAngleV >= Math.PI / 2)
			focusAngleV = Math.min(focusAngleV, Math.PI / 2 - 0.0005);
		updateSpeed();
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			w = true;
			break;
		case KeyEvent.VK_A:
			a = true;
			break;
		case KeyEvent.VK_S:
			s = true;
			break;
		case KeyEvent.VK_D:
			d = true;
			break;
		case KeyEvent.VK_SPACE:
			if (onGround())
				velocity.y = JUMP;
			break;
		}
		updateSpeed();
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			w = false;
			break;
		case KeyEvent.VK_A:
			a = false;
			break;
		case KeyEvent.VK_S:
			s = false;
			break;
		case KeyEvent.VK_D:
			d = false;
			break;
		}
		updateSpeed();
	}
	
	private void updateSpeed() {
		double speed = WALKING_SPEED;
		int up = 0;
		int right = 0;
		if (w)
			up++;
		if (a)
			right--;
		if (s)
			up--;
		if (d)
			right++;
		if (Math.abs(up) + Math.abs(right) == 2)
			speed /= Math.sqrt(2);
		Vector3 front = new Vector3(Math.cos(focusAngleH), 0, Math.sin(focusAngleH));
		Vector3 side = Vector3.crossProduct(front, Vector3.up());
		front.normalize();
		front.scale(speed * up);
		velocity.x = front.x;
		velocity.z = front.z;
		side.normalize();
		side.scale(speed * right);
		velocity.add(side);		
	}
	
	public boolean onGround() {
		return Game.getInstance().isObstructed(Vector3.sum(getFeetPos(), new Vector3(0, -0.1, 0)));
	}
	
	public Vector3 getHeadPos() {
		return Vector3.sum(pos, new Vector3(0, PLAYER_HEIGHT, 0));
	}
	
	public Vector3 getFeetPos() {
		return pos;
	}
	
	public Vector3 getFocus() {
		updateFocusPos();
		return focus;
	}
}
