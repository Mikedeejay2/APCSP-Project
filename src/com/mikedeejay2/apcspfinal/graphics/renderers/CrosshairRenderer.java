package com.mikedeejay2.apcspfinal.graphics.renderers;

import com.mikedeejay2.apcspfinal.graphics.crosshair.Crosshair;
import com.mikedeejay2.apcspfinal.graphics.shaders.CrosshairShader;
import com.mikedeejay2.apcspfinal.io.Window;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

public class CrosshairRenderer
{
    private CrosshairShader shader;

    public CrosshairRenderer() {
        shader = new CrosshairShader();
    }

    public void cleanUp(){
        shader.cleanUp();
    }

    public void render(Crosshair crosshair)
    {
        prepare();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, crosshair.getTexture());
        renderCrosshair(crosshair);
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

    private void renderCrosshair(Crosshair crosshair)
    {
        GL30.glBindVertexArray(crosshair.getMeshVao());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        shader.loadColor(crosshair.getColor());
        shader.loadTranslation(new Vector2f(1f/(Window.getWindowWidth()/2.0f), 1f/(Window.getWindowHeight()/2.0f)));
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, crosshair.getVertexCount());
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
