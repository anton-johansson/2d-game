package com.antonjohansson.game.client.app.asset.shader;

import org.lwjgl.opengl.ARBFragmentShader;

/**
 * Loads {@link FragmentShader fragment shaders}.
 */
public class FragmentShaderLoader extends AbstractShaderLoader<FragmentShader>
{
    public FragmentShaderLoader()
    {
        super(".fs", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB, FragmentShader::new);
    }
}
