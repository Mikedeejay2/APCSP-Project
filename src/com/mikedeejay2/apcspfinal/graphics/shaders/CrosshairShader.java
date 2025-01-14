package com.mikedeejay2.apcspfinal.graphics.shaders;

import org.joml.Vector2f;
import org.joml.Vector3f;

/*
 * This class is loosely organized off of ThinMatrix's
 * LWJGL tutorials, but was created by me without
 * rewatching any tutorials. I learned LWJGL from ThinMatrix,
 * so similarities will be visible.
 */
public class CrosshairShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "res/shaders/crosshairVertex.glsl";
	private static final String FRAGMENT_FILE = "res/shaders/crosshairFragment.glsl";

	private int location_color;
	private int location_translation;

	public CrosshairShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadColor(Vector3f color){
		super.loadVector(location_color, color);
	}

	public void loadTranslation(Vector2f translation){
		super.load2DVector(location_translation, translation);
	}


}
