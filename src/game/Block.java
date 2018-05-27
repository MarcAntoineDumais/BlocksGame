package game;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import rendering.Cube;
import rendering.Vector3;

public class Block {
	private BlockType type;
	
	public Block(BlockType type) {
		this.type = type;
	}
	
	public BlockType getType() {
		return type;
	}
	
	public void setType(BlockType type) {
		this.type = type;
	}
	
	public boolean isStopsMovement() {
		return type.isStopsMovement();
	}
	
	public void tick() {
	}
	
	public void render(Vector3 pos, GL2 gl) {
		ResourcesSingleton res = ResourcesSingleton.getInstance();
		gl.glColor3d(1, 1, 1);
		switch(type) {		
		case DIRT:
			res.getTexturesTop().get(InfoTexture.DIRT.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.render(pos, gl);
			break;
		case STONE:
			res.getTexturesTop().get(InfoTexture.STONE.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.render(pos, gl);
			break;
		case COBBLESTONE:
			res.getTexturesTop().get(InfoTexture.COBBLESTONE.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.render(pos, gl);
			break;
		case WOOD:
			res.getTexturesTop().get(InfoTexture.WOOD.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.renderTop(pos, gl);
			Cube.renderBottom(pos, gl);
			res.getTexturesSide().get(InfoTexture.WOOD.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.renderSides(pos, gl);
			break;
		case GRASS:
			gl.glColor3d(126/255.0, 200/255.0, 90/255.0);
			res.getTexturesTop().get(InfoTexture.GRASS.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.renderTop(pos, gl);
			gl.glColor3d(1, 1, 1);
			res.getTexturesSide().get(InfoTexture.GRASS.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.renderSides(pos, gl);
			res.getTexturesBottom().get(InfoTexture.GRASS.getNumero()).bind(gl);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			Cube.renderBottom(pos, gl);
			break;
		default:
			break;
		}
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
	}

}
