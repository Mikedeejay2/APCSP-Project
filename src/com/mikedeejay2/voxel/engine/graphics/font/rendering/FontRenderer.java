package com.mikedeejay2.voxel.engine.graphics.font.rendering;


import com.mikedeejay2.voxel.engine.loaders.font.creator.FontType;
import com.mikedeejay2.voxel.engine.loaders.font.creator.GUIText;
import org.joml.Vector2f;
import org.lwjgl.opengl.*;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class FontRenderer {

	private FontShader shader;

	public FontRenderer() {
		shader = new FontShader();
	}

	public void cleanUp(){
		shader.cleanUp();
	}

	public void render(Map<FontType, List<GUIText>> texts)
	{
		prepare();
		for(FontType font : texts.keySet()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for(GUIText text : texts.get(font)){
				renderText(text);
			}
		}
		endRendering();
	}

	private void prepare()
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		shader.start();
	}

	private void renderText(GUIText text)
	{
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		if(text.hasShadow())
		{
			shader.loadColor(text.getShadowColor());
			shader.loadTranslation(new Vector2f(text.getPosition().x + text.getFontSize()/600, text.getPosition().y + text.getFontSize()/400));
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		}
		shader.loadColor(text.getColor());
		shader.loadTranslation(text.getPosition());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	private void endRendering()
	{
		shader.stop();
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
	}

	public void windowHasBeenResized()
	{

	}
}
