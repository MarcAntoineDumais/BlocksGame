package rendering;

import com.jogamp.opengl.GL2;

public class Cube {
	public static void render(Vector3 pos, GL2 gl) {		
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		yPos(gl);
		yNeg(gl);
		xNeg(gl);
		xPos(gl);
		zNeg(gl);
		zPos(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	public static void renderYPos(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		yPos(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void yPos(GL2 gl) {
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
	
	public static void renderYNeg(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		yNeg(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void yNeg(GL2 gl) {
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
	
	public static void renderXNeg(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		xNeg(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void xNeg(GL2 gl) {
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
	
	public static void renderXPos(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		xPos(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void xPos(GL2 gl) {
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
	
	public static void renderZNeg(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		zNeg(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void zNeg(GL2 gl) {
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
	
	public static void renderZPos(Vector3 pos, GL2 gl) {
		gl.glPushMatrix();
		gl.glTranslated(pos.x, pos.y, pos.z);
		gl.glBegin(GL2.GL_QUADS);
		zPos(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
	
	private static void zPos(GL2 gl) {
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
		zNeg(gl);
		zPos(gl);
		xNeg(gl);
		xPos(gl);
		gl.glEnd();
		gl.glPopMatrix();
	}
}
