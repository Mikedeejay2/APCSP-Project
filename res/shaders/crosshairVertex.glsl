/*
 * This code was created by me.
 * I put this here because a lot of GLSL
 * code looks very similar. Please keep
 * this in mind when looking at GLSL code.
*/
#version 330

in vec2 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform vec2 translation;

void main(void){

    gl_Position = vec4(position + translation * vec2(2.0, -2.0), 0.0, 1.0);
    pass_textureCoords = textureCoords;

}