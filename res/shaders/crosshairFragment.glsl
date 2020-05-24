/*
 * This code was created by me.
 * I put this here because a lot of GLSL
 * code looks very similar. Please keep
 * this in mind when looking at GLSL code.
*/

#version 330

in vec2 pass_textureCoords;

out vec4 out_color;

uniform vec3 color;
uniform sampler2D fontAtlas;

void main(void){

    out_color = vec4(color, texture(fontAtlas, pass_textureCoords).a);

}