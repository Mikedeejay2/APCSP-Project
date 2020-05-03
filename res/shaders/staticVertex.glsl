#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec3 color;
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out float visibility;

const float density = 0.004;
const float gradient = 1.5;

void main(void)
{
    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelativeToCam;
    pass_textureCoords = textureCoords;
    color = vec3(position.x + 0.5, 0.0, position.y + 0.5);

    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}