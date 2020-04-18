package engine.graphics.shaders;

import engine.maths.Matrix4f;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20C.*;

public abstract class ShaderProgram
{
    int programID;
    int vertexShaderID;
    int fragmentShaderID;

    public ShaderProgram(String vertexFile, String fragmentFile)
    {
        programID = GL20.glCreateProgram();
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(String variableName, int attribute)
    {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    public void start()
    {
        GL20.glUseProgram(programID);
    }

    public void stop()
    {
        GL20.glUseProgram(0);
    }

    public void destroy()
    {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    private int loadShader(String file, int type)
    {
        StringBuilder shaderSource = new StringBuilder();

        InputStream in = Class.class.getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try
        {
            while((line = reader.readLine()) != null)
            {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Could not load shader file!");
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE)
        {
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 1000));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }

        return shaderID;
    }

    public int getUniformLocation(String name)
    {
        return glGetUniformLocation(programID, name);
    }

    public void setUniform(String name, float value)
    {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, int value)
    {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform(String name, boolean value)
    {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void setUniform(String name, Vector2f value)
    {
        glUniform2f(getUniformLocation(name), value.getX(), value.getY());
    }

    public void setUniform(String name, Vector3f value)
    {
        glUniform3f(getUniformLocation(name), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String name, Matrix4f value)
    {
        FloatBuffer matrix = MemoryUtil.memAllocFloat(4 * 4);
        matrix.put(value.getAll()).flip();
        glUniformMatrix4fv(getUniformLocation(name), true, matrix);
    }
}
