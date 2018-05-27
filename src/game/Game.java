package game;
import rendering.Vector3;

public class Game implements Runnable {
	//Animation
	private static Game game;
	private Thread processus;
	public static final double TICK_TIME = 0.05;
	private double timeSinceLastTick = TICK_TIME;
	private boolean paused = false;
	private boolean gameRunning = false;
	
	//Game
	private Player player;
	private World world;
	private Sun sun;


	private Game() {}
	
	public static Game getInstance() {
		if (game == null)
			game = new Game();
		return game;
	}
	
	public void newGame() {
		player = new Player(new Vector3(5, 80, 5));
		world = new World();
		sun = new Sun();
		processus = new Thread(this);
		processus.start();
	}
	
	public void endGame() {
		gameRunning = false;
	}

	public void run() {
		gameRunning = true;
		double t = System.currentTimeMillis();
		while (gameRunning) {
			if(!paused)
				update((System.currentTimeMillis() - t) / 1000.0);
			t = System.currentTimeMillis();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(double delta) {
		timeSinceLastTick += delta;
		while (timeSinceLastTick > TICK_TIME) {
			tick();
			timeSinceLastTick -= TICK_TIME;
		}
	}
	
	public void tick() {
		player.tick();
		world.tick();
		sun.tick();
	}
	
	public boolean isObstructed(Vector3 pos) {
		return world.isObstructed(pos);
	}
	
	
	public void play() {
		paused = false;
	}

	public void pause() {
		paused = true;
	}

	public boolean isPaused() {
		return paused;
	}

	public Player getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}

	public Sun getSun() {
		return sun;
	}

}
