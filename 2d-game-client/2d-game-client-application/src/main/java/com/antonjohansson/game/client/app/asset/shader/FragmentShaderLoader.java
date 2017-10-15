package com.antonjohansson.game.client.app.asset.shader;

import org.lwjgl.opengl.GL20;

/**
 * Loads {@link FragmentShader fragment shaders}.
 */
public class FragmentShaderLoader extends AbstractShaderLoader<FragmentShader>
{
    public FragmentShaderLoader()
    {
        super(".fs", GL20.GL_FRAGMENT_SHADER, FragmentShader::new);
    }
}
