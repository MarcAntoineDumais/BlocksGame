package game;

import static com.jogamp.opengl.GL.GL_TEXTURE_2D;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import rendering.Cube;
import rendering.Vector3;

public class Block {
    private BlockType type;
    private final static ResourcesSingleton res = ResourcesSingleton.getInstance();

    public Block(final BlockType type) {
        this.type = type;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(final BlockType type) {
        this.type = type;
    }

    public boolean isStopsMovement() {
        return type.isStopsMovement();
    }

    public void tick() {
    }

    public void renderXNeg(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderXNeg(pos, gl);
                break;
            case SIDES:
            case SIDES_TOP_BOTTOM:
                loadSideTexture(gl);
                Cube.renderXNeg(pos, gl);
                break;
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void renderXPos(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderXPos(pos, gl);
                break;
            case SIDES:
            case SIDES_TOP_BOTTOM:
                loadSideTexture(gl);
                Cube.renderXPos(pos, gl);
                break;
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void renderYNeg(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderYNeg(pos, gl);
                break;
            case SIDES:
                loadTopTexture(gl);
                Cube.renderYNeg(pos, gl);
                break;
            case SIDES_TOP_BOTTOM:
                loadBottomTexture(gl);
                Cube.renderYNeg(pos, gl);
                break;
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void renderYPos(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderYPos(pos, gl);
                break;
            case SIDES:
                loadTopTexture(gl);
                Cube.renderYPos(pos, gl);
                break;
            case SIDES_TOP_BOTTOM:
                if (type == BlockType.GRASS)
                    gl.glColor3d(126 / 255.0, 200 / 255.0, 90 / 255.0);
                loadTopTexture(gl);
                Cube.renderYPos(pos, gl);
                break;
        }


        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void renderZNeg(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderZNeg(pos, gl);
                break;
            case SIDES:
            case SIDES_TOP_BOTTOM:
                loadSideTexture(gl);
                Cube.renderZNeg(pos, gl);
                break;
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void renderZPos(final Vector3 pos, final GL2 gl) {
        if (type == BlockType.AIR)
            return;

        gl.glColor3d(1, 1, 1);
        switch (type.getRenderingType()) {
            case UNIFORM:
                loadTopTexture(gl);
                Cube.renderZPos(pos, gl);
                break;
            case SIDES:
            case SIDES_TOP_BOTTOM:
                loadSideTexture(gl);
                Cube.renderZPos(pos, gl);
                break;
        }

        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    public void loadTopTexture(final GL2 gl) {
        switch (type) {
            case DIRT:
                res.getTexturesTop().get(InfoTexture.DIRT.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case STONE:
                res.getTexturesTop().get(InfoTexture.STONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case COBBLESTONE:
                res.getTexturesTop().get(InfoTexture.COBBLESTONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case WOOD:
                res.getTexturesTop().get(InfoTexture.WOOD.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case GRASS:
                res.getTexturesTop().get(InfoTexture.GRASS.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            default:
                break;
        }
    }

    public void loadBottomTexture(final GL2 gl) {
        switch (type) {
            case DIRT:
                res.getTexturesBottom().get(InfoTexture.DIRT.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case STONE:
                res.getTexturesBottom().get(InfoTexture.STONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case COBBLESTONE:
                res.getTexturesBottom().get(InfoTexture.COBBLESTONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case WOOD:
                res.getTexturesBottom().get(InfoTexture.WOOD.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case GRASS:
                res.getTexturesBottom().get(InfoTexture.GRASS.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            default:
                break;
        }
    }

    public void loadSideTexture(final GL2 gl) {
        switch (type) {
            case DIRT:
                res.getTexturesSide().get(InfoTexture.DIRT.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case STONE:
                res.getTexturesSide().get(InfoTexture.STONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case COBBLESTONE:
                res.getTexturesSide().get(InfoTexture.COBBLESTONE.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case WOOD:
                res.getTexturesSide().get(InfoTexture.WOOD.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            case GRASS:
                res.getTexturesSide().get(InfoTexture.GRASS.getNumero()).bind(gl);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
                gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
                break;
            default:
                break;
        }
    }
}
