package engine.font.rendering;

import engine.graphics.shaders.ShaderProgram;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class FontShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/shaders/fontVertex.glsl";
	private static final String FRAGMENT_FILE = "/shaders/fontFragment.glsl";

	private int location_color;
	private int location_translation;

	public FontShader() {
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

	protected void loadColor(Vector3f colour){
		super.loadVector(location_color, colour);
	}

	protected void loadTranslation(Vector2f translation){
		super.load2DVector(location_translation, translation);
	}


}
