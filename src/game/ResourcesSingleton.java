package game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GLProfile;

import application.Application;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class ResourcesSingleton {
	private static ResourcesSingleton instance;
	private ArrayList<Texture> texturesTop = new ArrayList<Texture>();
	private ArrayList<Image> imagesTop = new ArrayList<Image>();
	private ArrayList<Texture> texturesSide = new ArrayList<Texture>();
	private ArrayList<Image> imagesSide = new ArrayList<Image>();
	private ArrayList<Texture> texturesBottom = new ArrayList<Texture>();
	private ArrayList<Image> imagesBottom = new ArrayList<Image>();
	private Image icons;
	
	//Constructeur prive pour empecher d'instancier le singleton	
	private ResourcesSingleton() {}
	
	public void loadTextures() {
		try {
			InputStream stream;
			TextureData data;
			InfoTexture[] infos = InfoTexture.values();

			for(int i = 0; i < infos.length; i++){
				stream = Application.class.getClassLoader().getResourceAsStream(infos[i].getTextureTop());
				data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				texturesTop.add(TextureIO.newTexture(data));
				stream = Application.class.getClassLoader().getResourceAsStream(infos[i].getTextureSide());
				data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				texturesSide.add(TextureIO.newTexture(data));
				stream = Application.class.getClassLoader().getResourceAsStream(infos[i].getTextureBottom());
				data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
				texturesBottom.add(TextureIO.newTexture(data));
				
				imagesTop.add(ImageIO.read(Application.class.getClassLoader().getResource(infos[i].getTextureTop())));
				imagesSide.add(ImageIO.read(Application.class.getClassLoader().getResource(infos[i].getTextureSide())));
				imagesBottom.add(ImageIO.read(Application.class.getClassLoader().getResource(infos[i].getTextureBottom())));
			}
			
			icons = ImageIO.read(Application.class.getClassLoader().getResource("icons.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Texture> getTexturesTop() {
		return texturesTop;
	}
	
	public ArrayList<Texture> getTexturesSide() {
		return texturesSide;
	}
	
	public ArrayList<Texture> getTexturesBottom() {
		return texturesBottom;
	}
	
	public ArrayList<Image> getImagesTop() {
		return imagesTop;
	}
	
	public ArrayList<Image> getImagesSide() {
		return imagesSide;
	}
	
	public ArrayList<Image> getImagesBottom() {
		return imagesBottom;
	}
	
	public Image getIcons() {
		return icons;
	}
	
	public static ResourcesSingleton getInstance() {
		if (instance == null) {
			synchronized(ResourcesSingleton.class) {
				if (instance == null) {
					instance = new ResourcesSingleton();
				}
			}
		}
		return instance;
	}
}
