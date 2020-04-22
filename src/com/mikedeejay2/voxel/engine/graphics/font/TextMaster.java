//package com.mikedeejay2.voxelengine.engine.font;
//
//import com.mikedeejay2.voxelengine.engine.font.creator.FontType;
//import com.mikedeejay2.voxelengine.engine.font.creator.GUIText;
//import com.mikedeejay2.voxelengine.engine.font.creator.TextMeshData;
//import com.mikedeejay2.voxelengine.engine.font.rendering.FontRenderer;
//import com.mikedeejay2.voxelengine.engine.loaders.Loader;
//import org.lwjgl.opengl.GL30;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TextMaster
//{
//    private static Loader loader;
//    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
//    private static FontRenderer renderer;
//
//    public static void init(Loader theLoader){
//        renderer = new FontRenderer();
//        loader = theLoader;
//    }
//
//    public static void render(){
//        renderer.render(texts);
//    }
//
//    public static void loadText(GUIText text){
//        FontType font = text.getFont();
//        TextMeshData data = font.loadText(text);
//        int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
//        text.setMeshInfo(vao, data.getVertexCount());
//        List<GUIText> textBatch = texts.get(font);
//        if(textBatch == null){
//            textBatch = new ArrayList<GUIText>();
//            texts.put(font, textBatch);
//        }
//        textBatch.add(text);
//    }
//
//    public static void loadTextUseVAO(GUIText text){
//        FontType font = text.getFont();
//        TextMeshData data = font.loadText(text);
//        int vao = text.getMesh();
//        text.setMeshInfo(vao, data.getVertexCount());
//        List<GUIText> textBatch = texts.get(font);
//        if(textBatch == null){
//            textBatch = new ArrayList<GUIText>();
//            texts.put(font, textBatch);
//        }
//        textBatch.add(text);
//    }
//
//    public static void editText(GUIText text, String newText)
//    {
//        removeText(text);
//        text.setText(newText);
//        loadText(text);
//    }
//
//    public static void removeText(GUIText text){
//        List<GUIText> textBatch = texts.get(text.getFont());
//        textBatch.remove(text);
//        if(textBatch.isEmpty()){
//            texts.remove(texts.get(text.getFont()));
//            GL30.glDeleteVertexArrays(text.getMesh());
//        }
//    }
//
//    public static void cleanUp(){
//        renderer.cleanUp();
//    }
//}
