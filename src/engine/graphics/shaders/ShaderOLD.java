//package engine.graphics.shaders;
//
//import engine.utils.FileUtils;
//import org.lwjgl.opengl.GL20;
//
//import static org.lwjgl.opengl.GL20C.*;
//
//public class ShaderProgram
//{
//    private String vertexFile, fragmentFile;
//    private int vertexID, fragmentID, programID;
//
//    public ShaderProgram(String vertexPath, String fragmentPath)
//    {
//        vertexFile = FileUtils.loadAsString(vertexPath);
//        fragmentFile = FileUtils.loadAsString(fragmentPath);
//    }
//
//    public void loadShader()
//    {
//        programID = glCreateProgram();
//        vertexID = glCreateShader(GL_VERTEX_SHADER);
//
//        glShaderSource(vertexID, vertexFile);
//        glCompileShader(vertexID);
//
//        if(glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE)
//        {
//            System.err.println("Vertex Shader: " + glGetShaderInfoLog(vertexID));
//            return;
//        }
//
//        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
//
//        glShaderSource(fragmentID, fragmentFile);
//        glCompileShader(fragmentID);
//
//        if(glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE)
//        {
//            System.err.println("Fragment Shader: " + glGetShaderInfoLog(fragmentID));
//            return;
//        }
//
//        glAttachShader(programID, vertexID);
//        glAttachShader(programID, fragmentID);
//
//        glLinkProgram(programID);
//        if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
//        {
//            System.err.println("Program Linking: " + glGetProgramInfoLog(programID));
//            return;
//        }
//
//        glValidateProgram(programID);
//        if(glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE)
//        {
//            System.err.println("Program Validation: " + glGetProgramInfoLog(programID));
//            return;
//        }
//
//        glDeleteShader(vertexID);
//        glDeleteShader(fragmentID);
//    }
//
//    public void bind()
//    {
//        glUseProgram(programID);
//    }
//
//    public void unbind()
//    {
//        glUseProgram(0);
//    }
//
//    public void destroy()
//    {
//        glDeleteProgram(programID);
//    }
//}
