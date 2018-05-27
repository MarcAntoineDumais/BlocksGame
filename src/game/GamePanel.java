package game;
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_TEXTURE_2D;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import javax.swing.JPanel;

import application.Application;
import rendering.Cube;
import rendering.Vector3;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;

public class GamePanel extends GLJPanel implements GLEventListener{
	private static final long serialVersionUID = -2108683247869951858L;
	
	//Rendering
	private final int OBJECTIF_FPS = 60;
	private double fps;
	private GL2 gl;
	private GLU glu;
	private double fov = 70; //Field of view (degrees)
	private double gameHeight = Application.WINDOW_HEIGHT;
	private FPSAnimator animator;
	private TextRenderer pauseRenderer, statsRenderer;
	private ArrayList<Texture> textures;
	private GLUquadric skybox;
	private Cursor blankCursor, defaultCursor;
	private boolean showStats = false;
	private double[][] frustum;
	private boolean firstPerson = true;
	private JPanel hud;

	//Controls
	private Robot robot;
	private boolean ignoreMouseMove = false;
	private boolean cursorPosInitiated = false;
	
	//Others
	private Game game;

	public GamePanel() {
		game = Game.getInstance();
		setFocusable(true);
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		defaultCursor = Cursor.getDefaultCursor();
		setCursor(blankCursor);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		addGLEventListener(this);
		frustum = new double[6][4];
		
		setLayout(new BorderLayout());
		hud = new JPanel() {
			private static final long serialVersionUID = 1750576083483753838L;
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image icons = ResourcesSingleton.getInstance().getIcons();
				g.drawImage(icons, getWidth() / 2 - 16, getHeight() / 2 - 16,
						getWidth() / 2 + 16, getHeight() / 2 + 16,
						0, 0, 16, 16, null);
				Image block = ResourcesSingleton.getInstance().getImagesSide().get(game.getWorld().getInfoTexture().getNumero());
				g.drawImage(block, getWidth() - 50, getHeight() - 50,
						40, 40, null);
			}
		};
		hud.setOpaque(false);
		add(hud, BorderLayout.CENTER);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseDraggedHandler(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMovedHandler(e);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedHandler(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseReleasedHandler(e);
			}
		});

		addMouseWheelListener(this::mouseWheelMovedHandler);

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyPressedHandler(e);
			}
			public void keyReleased(KeyEvent e) {
				keyReleasedHandler(e);
			}
		});

		ResourcesSingleton res = ResourcesSingleton.getInstance();
		res.loadTextures();
		textures = res.getTexturesTop();

		gl = drawable.getGL().getGL2();
		glu = new GLU();

		//Skybox
		skybox = glu.gluNewQuadric();

		//OpenGL initialization
		gl.glViewport(0, 0, getWidth(), getHeight());
		gl.glMatrixMode(GL_PROJECTION);
		glu.gluPerspective(fov, (float)getWidth() / getHeight() , 0.1, 1E38);
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glEnable(GL_TEXTURE_2D);
		gl.glEnable(GL_COLOR_MATERIAL);

		pauseRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 50));
		statsRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 16));
		animator = new FPSAnimator(this, OBJECTIF_FPS, true);
		animator.setUpdateFPSFrames(15, null);
		animator.start();

		
		//lighting
		float[] noAmbient = {0.2f, 0.2f, 0.2f, 1f};     // low ambient light
		float[] diffuse = {1f, 1f, 1f, 1f};        // full diffuse colour
//		float[] specular = {1f, 1f, 1f, 1f};
		float[] shininess = {50f};
		
		gl.glEnable(GL2.GL_LIGHT0);
//		gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, specular, 0);  
	    gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SHININESS, shininess, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		
		Game.getInstance().newGame();
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		requestFocusInWindow();
		
		fps = animator.getLastFPS();

		//Clears screen
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
		
		//Places camera & lightning
		Player player = game.getPlayer();
		Sun sun = game.getSun();
		Vector3 head = player.getHeadPos();
		Vector3 focus = player.getFocus();
		Vector3 diff = Vector3.difference(focus, head);
		if (firstPerson)
			diff.scale(0);
		else
			diff.scale(4);
		head.subtract(diff);
		glu.gluLookAt(head.x, head.y, head.z, focus.x, focus.y, focus.z, 0, 1, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, sun.getLightPos(), 0);	
		
		World world = game.getWorld();
		extractFrustum(gl);
		world.updateLists(gl, frustum);
		
		//Skybox
		gl.glPushMatrix();
		gl.glTranslated(head.x, head.y, head.z);
		gl.glRotated(-90, 1, 0, 0);
		textures.get(InfoTexture.SKYBOX.getNumero()).bind(gl);
		gl.glColor3d(1, 1, 1);
		glu.gluQuadricTexture(skybox, true);
		gl.glDepthMask(false);
		glu.gluSphere(skybox, 10, 100, 100);
		gl.glDepthMask(true);
		gl.glPopMatrix();
		gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
		
		//Sun
		Vector3 scaledPos = sun.getPos().copy();
		scaledPos.scale(0.1);
		gl.glPushMatrix();
		gl.glScaled(30, 30, 30);
		gl.glColor3d(1, 1, 0);
		Cube.render(scaledPos, gl);
		gl.glPopMatrix();
		long t = System.currentTimeMillis();
		gl.glEnable(GL2.GL_LIGHTING);
		world.render(gl);
		gl.glDisable(GL2.GL_LIGHTING);
		System.out.println("Blocks render time: " + (System.currentTimeMillis() - t));
		//Axes
		gl.glLineWidth(10);
		gl.glBegin(GL.GL_LINES);
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 60, 0);
		gl.glVertex3d(8, 60, 0);
		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 60, 0);
		gl.glVertex3d(0, 68, 0);
		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 60, 0);
		gl.glVertex3d(0, 60, 8);
		//Player
		if (!firstPerson) {
			gl.glColor3d(0, 1, 1);
			Vector3 v = player.getFeetPos();
			gl.glVertex3d(v.x, v.y, v.z);
			v = player.getHeadPos();
			gl.glVertex3d(v.x, v.y, v.z);
		}
		gl.glEnd();
		
		hud.repaint();
		
		//Pause text
		if (game.isPaused()) {
			pauseRenderer.beginRendering(getWidth(), getHeight());
			pauseRenderer.setColor(Color.LIGHT_GRAY);
			pauseRenderer.draw("GAME PAUSED", getWidth() / 2 - 170, getHeight() / 2 - 15);
			pauseRenderer.endRendering();
		}
		
		if (showStats) {
			statsRenderer.beginRendering(getWidth(), getHeight());
			statsRenderer.setColor(Color.WHITE);
			statsRenderer.draw("pos: " + game.getPlayer().getFeetPos(), 0, getHeight() - 16);
			statsRenderer.draw("Ground: " + player.onGround(), 0, getHeight() - 32);
			statsRenderer.draw("FPS: " + getFps(), 0, getHeight() - 48);
			statsRenderer.endRendering();
		}
	}

	/*
	 * http://www.crownandcutlass.com/features/technicaldetails/frustum.html
	 */
	public void extractFrustum(GL2 gl) {
	   double proj[] = new double[16];
	   double modl[] = new double[16];
	   double clip[] = new double[16];
	   double t;

	   /* Get the current PROJECTION matrix from OpenGL */
	   gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, proj, 0);

	   /* Get the current MODELVIEW matrix from OpenGL */
	   gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modl, 0);

	   /* Combine the two matrices (multiply projection by modelview) */
	   clip[ 0] = modl[ 0] * proj[ 0] + modl[ 1] * proj[ 4] + modl[ 2] * proj[ 8] + modl[ 3] * proj[12];
	   clip[ 1] = modl[ 0] * proj[ 1] + modl[ 1] * proj[ 5] + modl[ 2] * proj[ 9] + modl[ 3] * proj[13];
	   clip[ 2] = modl[ 0] * proj[ 2] + modl[ 1] * proj[ 6] + modl[ 2] * proj[10] + modl[ 3] * proj[14];
	   clip[ 3] = modl[ 0] * proj[ 3] + modl[ 1] * proj[ 7] + modl[ 2] * proj[11] + modl[ 3] * proj[15];

	   clip[ 4] = modl[ 4] * proj[ 0] + modl[ 5] * proj[ 4] + modl[ 6] * proj[ 8] + modl[ 7] * proj[12];
	   clip[ 5] = modl[ 4] * proj[ 1] + modl[ 5] * proj[ 5] + modl[ 6] * proj[ 9] + modl[ 7] * proj[13];
	   clip[ 6] = modl[ 4] * proj[ 2] + modl[ 5] * proj[ 6] + modl[ 6] * proj[10] + modl[ 7] * proj[14];
	   clip[ 7] = modl[ 4] * proj[ 3] + modl[ 5] * proj[ 7] + modl[ 6] * proj[11] + modl[ 7] * proj[15];

	   clip[ 8] = modl[ 8] * proj[ 0] + modl[ 9] * proj[ 4] + modl[10] * proj[ 8] + modl[11] * proj[12];
	   clip[ 9] = modl[ 8] * proj[ 1] + modl[ 9] * proj[ 5] + modl[10] * proj[ 9] + modl[11] * proj[13];
	   clip[10] = modl[ 8] * proj[ 2] + modl[ 9] * proj[ 6] + modl[10] * proj[10] + modl[11] * proj[14];
	   clip[11] = modl[ 8] * proj[ 3] + modl[ 9] * proj[ 7] + modl[10] * proj[11] + modl[11] * proj[15];

	   clip[12] = modl[12] * proj[ 0] + modl[13] * proj[ 4] + modl[14] * proj[ 8] + modl[15] * proj[12];
	   clip[13] = modl[12] * proj[ 1] + modl[13] * proj[ 5] + modl[14] * proj[ 9] + modl[15] * proj[13];
	   clip[14] = modl[12] * proj[ 2] + modl[13] * proj[ 6] + modl[14] * proj[10] + modl[15] * proj[14];
	   clip[15] = modl[12] * proj[ 3] + modl[13] * proj[ 7] + modl[14] * proj[11] + modl[15] * proj[15];

	   /* Extract the numbers for the RIGHT plane */
	   frustum[0][0] = clip[ 3] - clip[ 0];
	   frustum[0][1] = clip[ 7] - clip[ 4];
	   frustum[0][2] = clip[11] - clip[ 8];
	   frustum[0][3] = clip[15] - clip[12];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[0][0] * frustum[0][0] + frustum[0][1] * frustum[0][1] + frustum[0][2] * frustum[0][2] );
	   frustum[0][0] /= t;
	   frustum[0][1] /= t;
	   frustum[0][2] /= t;
	   frustum[0][3] /= t;

	   /* Extract the numbers for the LEFT plane */
	   frustum[1][0] = clip[ 3] + clip[ 0];
	   frustum[1][1] = clip[ 7] + clip[ 4];
	   frustum[1][2] = clip[11] + clip[ 8];
	   frustum[1][3] = clip[15] + clip[12];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[1][0] * frustum[1][0] + frustum[1][1] * frustum[1][1] + frustum[1][2] * frustum[1][2] );
	   frustum[1][0] /= t;
	   frustum[1][1] /= t;
	   frustum[1][2] /= t;
	   frustum[1][3] /= t;

	   /* Extract the BOTTOM plane */
	   frustum[2][0] = clip[ 3] + clip[ 1];
	   frustum[2][1] = clip[ 7] + clip[ 5];
	   frustum[2][2] = clip[11] + clip[ 9];
	   frustum[2][3] = clip[15] + clip[13];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[2][0] * frustum[2][0] + frustum[2][1] * frustum[2][1] + frustum[2][2] * frustum[2][2] );
	   frustum[2][0] /= t;
	   frustum[2][1] /= t;
	   frustum[2][2] /= t;
	   frustum[2][3] /= t;

	   /* Extract the TOP plane */
	   frustum[3][0] = clip[ 3] - clip[ 1];
	   frustum[3][1] = clip[ 7] - clip[ 5];
	   frustum[3][2] = clip[11] - clip[ 9];
	   frustum[3][3] = clip[15] - clip[13];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[3][0] * frustum[3][0] + frustum[3][1] * frustum[3][1] + frustum[3][2] * frustum[3][2] );
	   frustum[3][0] /= t;
	   frustum[3][1] /= t;
	   frustum[3][2] /= t;
	   frustum[3][3] /= t;

	   /* Extract the FAR plane */
	   frustum[4][0] = clip[ 3] - clip[ 2];
	   frustum[4][1] = clip[ 7] - clip[ 6];
	   frustum[4][2] = clip[11] - clip[10];
	   frustum[4][3] = clip[15] - clip[14];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[4][0] * frustum[4][0] + frustum[4][1] * frustum[4][1] + frustum[4][2] * frustum[4][2] );
	   frustum[4][0] /= t;
	   frustum[4][1] /= t;
	   frustum[4][2] /= t;
	   frustum[4][3] /= t;

	   /* Extract the NEAR plane */
	   frustum[5][0] = clip[ 3] + clip[ 2];
	   frustum[5][1] = clip[ 7] + clip[ 6];
	   frustum[5][2] = clip[11] + clip[10];
	   frustum[5][3] = clip[15] + clip[14];

	   /* Normalize the result */
	   t = Math.sqrt( frustum[5][0] * frustum[5][0] + frustum[5][1] * frustum[5][1] + frustum[5][2] * frustum[5][2] );
	   frustum[5][0] /= t;
	   frustum[5][1] /= t;
	   frustum[5][2] /= t;
	   frustum[5][3] /= t;
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		glu.gluDeleteQuadric(skybox);
		game.endGame();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		gl.glViewport(0, 0, width, height);

		//source: http://www.swiftless.com/tutorials/opengl/reshape.html
		fov = fov / 180 * Math.PI;
		fov = Math.atan(Math.tan( fov / 2.0 ) * height / gameHeight ) * 2.0 * 180 / Math.PI;
		gameHeight = height;
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(fov, (float)width / height , 0.1, 1E38);
		gl.glMatrixMode(GL_MODELVIEW);		
	}

	public void mouseDraggedHandler(MouseEvent e) {
		mouseMovedHandler(e);
	}

	public void mouseMovedHandler(MouseEvent e) {
		if (!cursorPosInitiated) {
			cursorPosInitiated = true;
			Point p = getLocationOnScreen();
			robot.mouseMove(p.x + getWidth() / 2, p.y + getHeight() / 2);
			return;
		}
		if (!game.isPaused() && !ignoreMouseMove) {
			int dx = e.getX() - getWidth() / 2;
			int dy = e.getY() - getHeight() / 2;

			Point p = getLocationOnScreen();
			ignoreMouseMove = true;
			robot.mouseMove(p.x + getWidth() / 2, p.y + getHeight() / 2);
			
			game.getPlayer().mouseMoved(dx, dy);
		} else
			ignoreMouseMove = false;
	}

	public void mousePressedHandler(MouseEvent e) {
		game.getWorld().mousePressedActions(e);
	}

	public void mouseReleasedHandler(MouseEvent e) {
	}

	public void mouseWheelMovedHandler(MouseWheelEvent e) {
		game.getWorld().mouseWheelMovedActions(e);
	}

	public void keyPressedHandler(KeyEvent e) {
		game.getPlayer().keyPressed(e);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			if (game.isPaused())
				play();
			else
				pause();
			break;
		case KeyEvent.VK_F3:
			showStats = !showStats;
			break;
		case KeyEvent.VK_F5:
			firstPerson = !firstPerson;
			break;
		}
	}

	public void keyReleasedHandler(KeyEvent e) {
		game.getPlayer().keyReleased(e);
	}

	
	public void play() {
		game.play();
		setCursor(blankCursor);
		Point p = getLocationOnScreen();
		robot.mouseMove(p.x + getWidth() / 2, p.y + getHeight() / 2);
	}

	public void pause() {
		game.pause();
		setCursor(defaultCursor);
	}

	public double getFps() {
		return fps;
	}

	public static double round(double value, int nDecimals) {
		return (int)(Math.round(value * Math.pow(10, nDecimals))) / Math.pow(10, nDecimals);
	}
}
