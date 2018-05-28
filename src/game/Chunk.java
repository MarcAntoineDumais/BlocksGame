package game;

import com.jogamp.opengl.GL2;

import rendering.Vector3;
import rendering.Vector3i;

public class Chunk {
    public final static int CHUNK_SIZE = 16;
    private Block[][][] blocks;
    private int[] pos;
    private int displayLists;
    private int nOccupied;

    public Chunk(int posX, int posY, int posZ) {
        nOccupied = 0;
        pos = new int[3];
        pos[0] = posX;
        pos[1] = posY;
        pos[2] = posZ;
        blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < blocks.length; x++)
            for (int y = 0; y < blocks[0].length; y++)
                for (int z = 0; z < blocks[0][0].length; z++) {
                    if (pos[1] * CHUNK_SIZE + y < 16 || Math.random() < 0.01) {
                        double r = Math.random();
                        if (r < 0.04)
                            blocks[x][y][z] = new Block(BlockType.WOOD);
                        else if (r < 0.16)
                            blocks[x][y][z] = new Block(BlockType.GRASS);
                        else if (r < 0.26)
                            blocks[x][y][z] = new Block(BlockType.STONE);
                        else if (r < 0.36)
                            blocks[x][y][z] = new Block(BlockType.COBBLESTONE);
                        else
                            blocks[x][y][z] = new Block(BlockType.DIRT);
                        nOccupied++;
                    } else {
                        blocks[x][y][z] = new Block(BlockType.AIR);
                    }
                }
    }

    public void tick() {
        for (int x = 0; x < blocks.length; x++)
            for (int y = 0; y < blocks[0].length; y++)
                for (int z = 0; z < blocks[0][0].length; z++)
                    blocks[x][y][z].tick();
    }

    public void createDisplayList(GL2 gl) {
        nOccupied = 0;
        displayLists = gl.glGenLists(6);
        if (displayLists == 0)
            return;
        for (int i = 0; i < 6; i++) {
            gl.glNewList(displayLists + i, GL2.GL_COMPILE);
            for (int x = 0; x < blocks.length; x++)
                for (int y = 0; y < blocks[0].length; y++)
                    for (int z = 0; z < blocks[0][0].length; z++) {
                        if (i == 0 && blocks[x][y][z].getType() != BlockType.AIR)
                            nOccupied++;
                        final Vector3 p = new Vector3(x + 0.5 + pos[0] * CHUNK_SIZE,
                                y + 0.5 + pos[1] * CHUNK_SIZE,
                                z + 0.5 + pos[2] * CHUNK_SIZE);

                        switch (i) {
                            case 0:
                                if (x == 0 || blocks[x - 1][y][z].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderXNeg(p, gl);
                                break;
                            case 1:
                                if (x == blocks.length - 1 || blocks[x + 1][y][z].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderXPos(p, gl);
                                break;
                            case 2:
                                if (y == 0 || blocks[x][y - 1][z].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderYNeg(p, gl);
                                break;
                            case 3:
                                if (y == blocks[0].length - 1 || blocks[x][y + 1][z].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderYPos(p, gl);
                                break;
                            case 4:
                                if (z == 0 || blocks[x][y][z - 1].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderZNeg(p, gl);
                                break;
                            case 5:
                                if (z == blocks[0][0].length - 1 || blocks[x][y][z + 1].getType() == BlockType.AIR)
                                    blocks[x][y][z].renderZPos(p, gl);
                                break;
                            default:
                                break;
                        }
                    }
            gl.glEndList();
        }
    }

    public int getDisplayLists() {
        return displayLists;
    }

    public boolean isObstructed(Vector3i pos) {
        if (pos.x < 0 || pos.x >= CHUNK_SIZE ||
                pos.y < 0 || pos.y >= CHUNK_SIZE ||
                pos.z < 0 || pos.z >= CHUNK_SIZE)
            return true;
        return blocks[pos.x][pos.y][pos.z].isStopsMovement();
    }

    public int[] getPos() {
        return pos;
    }

    public int getX() {
        return pos[0];
    }

    public int getY() {
        return pos[1];
    }

    public int getZ() {
        return pos[2];
    }

    public boolean isEmpty() {
        return nOccupied == 0;
    }

    public Block getBlock(Vector3i pos) {
        if (pos.x >= 0 && pos.x < blocks.length &&
                pos.y >= 0 && pos.y < blocks[0].length &&
                pos.z >= 0 && pos.z < blocks[0][0].length)
            return blocks[pos.x][pos.y][pos.z];
        return null;
    }

    public int getBoundXNeg() {
        return pos[0] * CHUNK_SIZE;
    }

    public int getBoundXPos() {
        return pos[0] * CHUNK_SIZE + CHUNK_SIZE;
    }

    public int getBoundYNeg() {
        return pos[1] * CHUNK_SIZE;
    }

    public int getBoundYPos() {
        return pos[1] * CHUNK_SIZE + CHUNK_SIZE;
    }

    public int getBoundZNeg() {
        return pos[2] * CHUNK_SIZE;
    }

    public int getBoundZPos() {
        return pos[2] * CHUNK_SIZE + CHUNK_SIZE;
    }
}
