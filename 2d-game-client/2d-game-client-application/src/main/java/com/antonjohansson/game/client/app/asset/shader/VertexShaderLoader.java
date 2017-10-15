package com.antonjohansson.game.client.app.asset.shader;

import org.lwjgl.opengl.GL20;

/**
 * Loads {@link VertexShader vertex shaders}.
 */
public class VertexShaderLoader extends AbstractShaderLoader<VertexShader>
{
    public VertexShaderLoader()
    {
        super(".vs", GL20.GL_VERTEX_SHADER, VertexShader::new);
    }
}
