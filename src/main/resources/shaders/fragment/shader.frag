#version 330

out vec4 color;

in vec3 vertexColor;

//uniform vec4 ourColor;

void main(){
//    color = vec4(0.0, 1.0, 0.0, 1.0);
//    color = vertexColor;
    color = vec4(vertexColor, 1.0);
}