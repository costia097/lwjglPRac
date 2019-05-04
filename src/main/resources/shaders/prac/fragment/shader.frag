#version 330

out vec4 color;

//in vec3 vertexColor;
//uniform vec4 ourColor;

//in vec3 outColor;
in vec2 texCoord;

uniform sampler2D texture1;

void main(){
//    color = vec4(0.0, 1.0, 0.0, 1.0);
//    color = vertexColor;
//    color = vec4(vertexColor, 1.0);

    color = texture(texture1,texCoord) * vec4(0.0, 1.0, 0.0, 0.0);
}