package game;

import rendering.Vector3;

public class Sun {
	private final static double DISTANCE = 1000;
	private final static double DAY_DURATION = 1200;
	private double angle;
	private Vector3 pos;
	
	public Sun() {
		angle = Math.PI * 1 / 4;
		pos = new Vector3(0, DISTANCE, 0);
	}
	
	public void tick() {
		angle+= Game.TICK_TIME / DAY_DURATION * Math.PI * 2;
	}
	
	public void updatePos() {
		pos.x = DISTANCE * Math.cos(angle);
		pos.y = DISTANCE * Math.sin(angle);
	}
	
	public float[] getLightPos() {
		updatePos();
		float[] lightPos = {(float)pos.x, (float)pos.y, (float)pos.z, 0};
		return lightPos;		
	}
	
	public Vector3 getPos() {
		return pos;
	}
}
