/*
 * Created using ThinMatrix's text
 * tutorial on Youtube. It's not the same
 * code, and I still coded it myself,
 * but the original idea was from that video.
*/
#version 330

in vec2 pass_textureCoords;

out vec4 out_color;

uniform vec3 color;
uniform sampler2D fontAtlas;

void main(void){

    out_color = vec4(color, texture(fontAtlas, pass_textureCoords).a);

}