package engine.font;

import engine.font.creator.FontType;
import engine.font.creator.GUIText;
import engine.font.creator.TextMeshData;
import engine.font.rendering.FontRenderer;
import engine.graphics.Loader;
import engine.graphics.Mesh;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextMaster
{
    private static Loader loader;
    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
    private static FontRenderer renderer;

    public static void init(Loader theLoader){
        renderer = new FontRenderer();
        loader = theLoader;
    }

    public static void render(){
        renderer.render(texts);
    }

    public static void loadText(GUIText text){
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.get(font);
        if(textBatch == null){
            textBatch = new ArrayList<GUIText>();
            texts.put(font, textBatch);
        }
        textBatch.add(text);
    }

    public static void removeText(GUIText text){
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty()){
            texts.remove(texts.get(text.getFont()));
        }
    }

    public static void cleanUp(){
        renderer.cleanUp();
    }
}
