package debug;

import engine.font.TextMaster;
import engine.font.creator.FontType;
import engine.font.creator.GUIText;
import engine.graphics.Texture;
import engine.io.Window;
import engine.maths.Vector2f;

import java.io.File;

public class DebugScreen
{
    boolean isEnabled;

    float fontSize;

    float fontColorR, fontColorG, fontColorB;

    FontType font;

    GUIText versionText;
    GUIText fpsText;

    public void init()
    {
        isEnabled = false;
        fontColorR = 1;
        fontColorG = 1;
        fontColorB = 1;
        fontSize = 0.8f;
    }

    public void update(float delta)
    {
        if(isEnabled)
        {
            TextMaster.editText(fpsText, Window.getFps() + " fps");
        }
    }

    public void toggle()
    {
        isEnabled = !isEnabled;
        if(isEnabled)
        {
            font = new FontType(new Texture("fonts/ascii.png").getId(), new File("res/fonts/ascii.fnt"));
            versionText = new GUIText("Mikedeejay2 Voxel Engine Alpha 1.0.0", fontSize, font, new Vector2f(0.003f, 0.0f), 1f, false);
            versionText.setcolor(1, 1, 1);
            fpsText = new GUIText(Window.getFps() + " fps", fontSize, font, new Vector2f(0.003f, 0.03f), 1f, false);
            fpsText.setcolor(1, 1, 1);
        }
        else
        {
            versionText.remove();
            fpsText.remove();
        }
    }
}
