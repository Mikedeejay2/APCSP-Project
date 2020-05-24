package com.mikedeejay2.apcspfinal.graphics.font;

import com.mikedeejay2.apcspfinal.graphics.renderers.FontRenderer;
import com.mikedeejay2.apcspfinal.loaders.Loader;
import com.mikedeejay2.apcspfinal.loaders.font.creator.FontType;
import com.mikedeejay2.apcspfinal.loaders.font.creator.GUIText;
import com.mikedeejay2.apcspfinal.loaders.font.creator.TextMeshData;
import com.mikedeejay2.apcspfinal.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Heavily modified version of ThinMatrix's
 * text rendering tutorial. Made things a ton
 * more efficient.
 */
public class TextMaster
{
    private static Loader loader;
    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
    private static FontRenderer renderer;

    private static Main instance;

    public static void init(Loader theLoader, Main main) {
        renderer = new FontRenderer();
        loader = theLoader;
        instance = main;
    }

    public static void windowHasBeenResized()
    {
        renderer.windowHasBeenResized();
    }

    public static void render(){
        renderer.render(texts);
    }

    public static void loadText(GUIText text){
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int[] vaovbo = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        int vao = vaovbo[0];
        text.setMeshInfo(vao, data.getVertexCount(), new int[] {vaovbo[1], vaovbo[2]});
        List<GUIText> textBatch = texts.get(font);
        if(textBatch == null){
            textBatch = new ArrayList<GUIText>();
            texts.put(font, textBatch);
        }
        textBatch.add(text);
    }

    public static void editText(GUIText text, String newText)
    {
        removeText(text);
        text.setText(newText);
        loadText(text);
    }

    public static void removeText(GUIText text)
    {
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        texts.remove(texts.get(text.getFont()));
        loader.deleteVAO(text.getTextMeshVao());
        for (int i = 0; i < text.getVbos().length; i++)
        {
            loader.deleteVBO(text.getVbos()[i]);
        }
    }

    public static void cleanUp(){
        renderer.cleanUp();
    }
}
