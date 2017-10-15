package com.antonjohansson.game.client.app.asset.shader;

import org.lwjgl.opengl.GL20;

import com.antonjohansson.game.client.app.asset.common.IAsset;

/**
 * Defines a shader program, including a vertex shader and a fragment shader.
 */
public class ShaderProgram implements IAsset
{
    private final String name;
    private final int handle;
    private final VertexShader vertexShader;
    private final FragmentShader fragmentShader;

    ShaderProgram(String name, int handle, VertexShader vertexShader, FragmentShader fragmentShader)
    {
        this.name = name;
        this.handle = handle;
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    @Override
    public Object getIdentifier()
    {
        return name;
    }

    int getHandle()
    {
        return handle;
    }

    VertexShader getVertexShader()
    {
        return vertexShader;
    }

    FragmentShader getFragmentShader()
    {
        return fragmentShader;
    }

    /**
     * Starts using the shader program.
     */
    public void use()
    {
        GL20.glUseProgram(handle);
    }

    /**
     * Stops using the shader program.
     */
    public void end()
    {
        GL20.glUseProgram(0);
    }
}
