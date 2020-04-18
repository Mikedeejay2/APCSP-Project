package engine.graphics.shaders;

public class StaticShader extends ShaderProgram
{
    private static final String vertexFile = "/shaders/staticVertex.glsl";
    private static final String fragmentFIle = "/shaders/staticFragment.glsl";

    public StaticShader()
    {
        super(vertexFile, fragmentFIle);
    }

    @Override
    protected void bindAttributes()
    {
        super.bindAttribute("position", 0);
        super.bindAttribute("in_Color", 1);
    }
}
