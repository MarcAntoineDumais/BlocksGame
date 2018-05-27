package rendering;

import com.jogamp.opengl.GL2;

public class Cube {
	public static void render(Vector3 pos, GL2 gl) {		
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		top(gl);
		bottom(gl);
		left(gl);
		right(gl);
		front(gl);
		back(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	public static void renderTop(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		top(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void top(GL2 gl) {
		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(-0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0.5,  0.5,  -0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-0.5,  0.5,  -0.5);
	}
	
	public static void renderBottom(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		bottom(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void bottom(GL2 gl) {
		gl.glNormal3d(0, -1, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(-0.5,  -0.5,  0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0.5,  -0.5,  0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0.5,  -0.5,  -0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-0.5,  -0.5,  -0.5);
	}
	
	public static void renderLeft(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		left(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void left(GL2 gl) {
		gl.glNormal3d(-1, 0, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(-0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(-0.5,  0.5,  -0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(-0.5,  -0.5,  -0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-0.5,  -0.5,  0.5);
	}
	
	public static void renderRight(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		right(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void right(GL2 gl) {
		gl.glNormal3d(1, 0, 0);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0.5,  0.5,  -0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0.5,  -0.5,  -0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(0.5,  -0.5,  0.5);
	}
	
	public static void renderFront(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		front(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void front(GL2 gl) {
		gl.glNormal3d(0, 0, -1);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(-0.5,  0.5,  -0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0.5,  0.5,  -0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0.5,  -0.5,  -0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-0.5,  -0.5,  -0.5);
	}
	
	public static void renderBack(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		back(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void back(GL2 gl) {
		gl.glNormal3d(0, 0, 1);
		gl.glTexCoord2d(0, 1);
		gl.glVertex3d(-0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 1);
		gl.glVertex3d(0.5,  0.5,  0.5);
		gl.glTexCoord2d(1, 0);
		gl.glVertex3d(0.5,  -0.5,  0.5);
		gl.glTexCoord2d(0, 0);
		gl.glVertex3d(-0.5,  -0.5,  0.5);
	}
	
	public static void renderSides(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		front(gl);
		back(gl);
		left(gl);
		right(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
}
