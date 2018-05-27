package game;

import rendering.RenderingType;

public enum BlockType {
	AIR(false, RenderingType.UNIFORM), DIRT(true, RenderingType.UNIFORM), GRASS(true, RenderingType.SIDES_TOP_BOTTOM),
    STONE(true, RenderingType.UNIFORM), WOOD(true, RenderingType.SIDES), COBBLESTONE(true,RenderingType.UNIFORM);
	
	private boolean stopsMovement;
	private RenderingType renderingType;
	
	BlockType(boolean stopsMovement, RenderingType renderingType) {
		this.stopsMovement = stopsMovement;
		this.renderingType = renderingType;
	}
	
	public boolean isStopsMovement() {
		return stopsMovement;
	}

	public RenderingType getRenderingType() {
	    return renderingType;
    }
}
