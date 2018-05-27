package game;

public enum InfoTexture {
	SKYBOX("sky.png", "sky.png", "sky.png", 0), DIRT("dirt.png", "dirt.png", "dirt.png", 1), 
	GRASS("grass_top.png", "dirt.png", "grass_side.png", 2), STONE("stone.png", "stone.png", "stone.png", 3), 
	WOOD("log_oak_top.png", "log_oak_top.png", "log_oak.png", 4), COBBLESTONE("cobblestone.png", "cobblestone.png", "cobblestone.png", 5);
	
	private final String textureTop, textureBottom, textureSide;
	private final int numero;
	
	InfoTexture(String top, String bottom, String side, int numero) {
		textureTop = top;
		textureSide = side;
		textureBottom = bottom;
		this.numero = numero;
	}
	
	public String getTextureTop() {
		return textureTop;
	}

	public String getTextureBottom() {
		return textureBottom;
	}
	
	public String getTextureSide() {
		return textureSide;
	}
	
	public int getNumero() {
		return numero;
	}
}
