package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.jogamp.opengl.GL2;

import rendering.Vector3;
import rendering.Vector3i;

public class World {
    private static final int WORLD_HEIGHT = 2;
    private static final int INITIAL_WORLD_SIZE = 2;
    private static final double PLAYER_ARM_RANGE = 4.6;
    private HashMap<Vector3i, Chunk> chunks;
    private ArrayList<Chunk> displayLists;
    private ArrayList<Chunk> listsToUpdate;
    private int chosenBlock = 1;

    public World() {
        chunks = new HashMap<>();
        displayLists = new ArrayList<>();
        listsToUpdate = new ArrayList<>();
        generateWorld();
    }

    public void generateWorld() {
        for (int x = -INITIAL_WORLD_SIZE; x < INITIAL_WORLD_SIZE; x++)
            for (int z = -INITIAL_WORLD_SIZE; z < INITIAL_WORLD_SIZE; z++)
                for (int y = 0; y < WORLD_HEIGHT; y++) {
                    Chunk c = new Chunk(x, y, z);
                    chunks.put(new Vector3i(x, y, z), c);
                    listsToUpdate.add(c);
                }
    }

    public void render(GL2 gl) {
        for (Chunk c : displayLists) {
            if (!c.isEmpty())
                gl.glCallList(c.getDisplayListIndex());
        }
    }

    public void updateLists(GL2 gl, double[][] frustum) {
        Iterator<Chunk> it = listsToUpdate.iterator();

        while (it.hasNext()) {
            Chunk c = it.next();
            c.createDisplayList(gl);
            it.remove();
        }

        displayLists.clear();
        for (Map.Entry<Vector3i, Chunk> entry : chunks.entrySet()) {
            Chunk c = entry.getValue();
            if (isSphereInFrustum((c.getX() + 0.5) * Chunk.CHUNK_SIZE,
                    (c.getY() + 0.5) * Chunk.CHUNK_SIZE,
                    (c.getZ() + 0.5) * Chunk.CHUNK_SIZE,
                    Chunk.CHUNK_SIZE / 2.0 * 2/*.41421356*/, frustum))
                displayLists.add(c);
        }
    }

    /*
     * http://www.crownandcutlass.com/features/technicaldetails/frustum.html
     */
    private boolean isSphereInFrustum(double x, double y, double z, double radius, double[][] frustum) {
        for (int p = 0; p < 6; p++)
            if (frustum[p][0] * x + frustum[p][1] * y + frustum[p][2] * z + frustum[p][3] <= -radius)
                return false;
        return true;
    }

    public void tick() {
        for (Map.Entry<Vector3i, Chunk> entry : chunks.entrySet())
            entry.getValue().tick();
    }

    public boolean isObstructed(Vector3 pos) {
        Chunk c = getChunk(pos);
        if (c == null)
            return false;
        return c.isObstructed(getBlockPos(pos));
    }

    public Chunk getChunk(Vector3 pos) {
        int x = (int) Math.floor(pos.x / Chunk.CHUNK_SIZE);
        int y = (int) Math.floor(pos.y / Chunk.CHUNK_SIZE);
        int z = (int) Math.floor(pos.z / Chunk.CHUNK_SIZE);
        return chunks.get(new Vector3i(x, y, z));
    }

    public Vector3i getBlockPos(Vector3 pos) {
        int x = (int) Math.floor(pos.x % Chunk.CHUNK_SIZE);
        if (x < 0)
            x += Chunk.CHUNK_SIZE;
        int y = (int) Math.floor(pos.y % Chunk.CHUNK_SIZE);
        if (y < 0)
            y += Chunk.CHUNK_SIZE;
        int z = (int) Math.floor(pos.z % Chunk.CHUNK_SIZE);
        if (z < 0)
            z += Chunk.CHUNK_SIZE;
        return new Vector3i(x, y, z);
    }

    public void mousePressedActions(MouseEvent e) {
        Player player = Game.getInstance().getPlayer();
        Vector3 rayPos = player.getHeadPos();
        Vector3 rayD = Vector3.difference(player.getFocus(), player.getHeadPos());
        rayD.normalize();
        rayD.scale(0.1);
        double distance = rayD.magnitude();
        rayPos.add(rayD);
        Block b = null, lastB = null;
        Chunk c = null, lastC = null;
        boolean solidBlockFound = false;
        while (distance < PLAYER_ARM_RANGE) {
            lastB = b;
            c = getChunk(rayPos);
            if (c != null)
                b = c.getBlock(getBlockPos(rayPos));
            if (b != null) {
                if (b.getType() != BlockType.AIR) {
                    solidBlockFound = true;
                    break;
                } else {
                    lastB = b;
                    lastC = c;
                }
            }
            rayPos.add(rayD);
            distance += rayD.magnitude();
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (b != null) {
                b.setType(BlockType.AIR);
                listsToUpdate.add(c);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (lastB != null && solidBlockFound && lastB != c.getBlock(getBlockPos(player.getHeadPos()))
                    && lastB != c.getBlock(getBlockPos(player.getFeetPos()))) {
                lastB.setType(getBlockType());
                listsToUpdate.add(lastC);
            }
        }
    }

    public void mouseWheelMovedActions(MouseWheelEvent e) {
        int dWheel = e.getWheelRotation();
        int nBlocks = BlockType.values().length - 1;
        chosenBlock += dWheel;
        chosenBlock = (chosenBlock - 1) % nBlocks;
        if (chosenBlock < 0)
            chosenBlock += nBlocks;
        chosenBlock++;
    }

    public BlockType getBlockType() {
        int n = 0;
        for (BlockType b : BlockType.values()) {
            if (chosenBlock == n)
                return b;
            n++;
        }

        return null;
    }

    public InfoTexture getInfoTexture() {
        int n = 0;
        for (InfoTexture i : InfoTexture.values()) {
            if (chosenBlock == n)
                return i;
            n++;
        }

        return null;
    }
}
