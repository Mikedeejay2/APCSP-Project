#version 400 core

in vec3 color;
in vec2 pass_textureCoords;
in float visibility;
in vec3 pass_brightness;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 skyColor;

void main(void)
{
    out_Color = texture(textureSampler, pass_textureCoords) * vec4(pass_brightness, 1.0);

    vec4 textureColor = texture(textureSampler, pass_textureCoords);
    if(textureColor.a < 0.5)
    {
        discard;
    }

    out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
}