#version 400 core

in vec3 position;
in vec3 in_Color;

out vec3 color;

void main(void)
{
    gl_Position = vec4(position, 1.0);
    color = in_Color;
}