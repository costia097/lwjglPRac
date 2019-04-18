#version 330

//layout (location = 0) in vec3 pos;

//layout(location= 0) in vec3 aPos;
//layout(location= 1) in vec3 aColor;

//out vec3 vertexColor;

//uniform mat4 model;

layout(location = 0) in vec3 aPos;
//layout(location = 1) in vec3 aColor;
layout(location = 1) in vec2 aTexCoord;

//out vec3 outColor;
out vec2 texCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(){
//    gl_Position = model * vec4(aPos , 1.0);
//    vertexColor = vec4(0.5, 1.0, 0.0, 1.0);
//    gl_Position = vec4(aPos, 1.0);
//    vertexColor = aColor;

//    gl_Position = vec4(aPos, 1.0);

//    outColor = aColor;
    texCoord = aTexCoord;

    gl_Position  = projection * view * model * vec4(aPos,1.0);
}
