package game;

public enum BlockType {
	AIR(false), DIRT(true), GRASS(true), STONE(true), WOOD(true), COBBLESTONE(true);
	
	private boolean stopsMovement;
	
	private BlockType(boolean stopsMovement) {
		this.stopsMovement = stopsMovement;
	}
	
	public boolean isStopsMovement() {
		return stopsMovement;
	}
}
