#version 330

//layout (location = 0) in vec3 pos;

layout(location= 0) in vec3 aPos;
layout(location= 1) in vec3 aColor;

out vec3 vertexColor;

uniform mat4 model;

void main(){
//    gl_Position = model * vec4(0.5 * pos.x  , 0.5 * pos.y, pos.z , 1.0);
//    vertexColor = vec4(0.5, 1.0, 0.0, 1.0);
    gl_Position = vec4(aPos, 1.0);
    vertexColor = aColor;
}
