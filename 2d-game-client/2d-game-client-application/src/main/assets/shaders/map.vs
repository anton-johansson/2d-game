#version 150 core

uniform mat4 param_projection;

in vec2 in_Position;
in vec2 in_TextureCoord;

out vec2 pass_TextureCoord;

void main(void)
{
    gl_Position = param_projection * vec4(in_Position, 0f, 1f);
    pass_TextureCoord = in_TextureCoord;
}
