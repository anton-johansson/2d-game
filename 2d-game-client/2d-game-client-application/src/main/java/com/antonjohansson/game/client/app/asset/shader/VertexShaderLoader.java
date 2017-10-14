package com.antonjohansson.game.client.app.asset.shader;

import org.lwjgl.opengl.ARBVertexShader;

/**
 * Loads {@link VertexShader vertex shaders}.
 */
public class VertexShaderLoader extends AbstractShaderLoader<VertexShader>
{
    public VertexShaderLoader()
    {
        super(".vs", ARBVertexShader.GL_VERTEX_SHADER_ARB, VertexShader::new);
    }
}
