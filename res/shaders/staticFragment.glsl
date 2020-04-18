#version 400 core

in vec3 passColor;
in vec2 passTextureCoord;

out vec4 out_Color;

uniform sampler2D tex;

void main(void)
{
    out_Color = texture(tex, passTextureCoord);
}