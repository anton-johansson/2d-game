package com.antonjohansson.game.client.app.asset.shader;

import static com.antonjohansson.game.client.math.Matrix4.NUMBER_OF_FLOATS;
import static java.util.Objects.requireNonNull;
import static org.lwjgl.BufferUtils.createFloatBuffer;

import java.nio.FloatBuffer;
import java.util.Map;

import org.lwjgl.opengl.GL20;

import com.antonjohansson.game.client.app.asset.common.IAsset;
import com.antonjohansson.game.client.math.Matrix4;

/**
 * Defines a shader program, including a vertex shader and a fragment shader.
 */
public class ShaderProgram implements IAsset
{
    private final String name;
    private final int handle;
    private final Map<String, Integer> parameterHandles;
    private final VertexShader vertexShader;
    private final FragmentShader fragmentShader;
    private boolean inUse;

    ShaderProgram(String name, int handle, Map<String, Integer> parameterHandles, VertexShader vertexShader, FragmentShader fragmentShader)
    {
        this.name = name;
        this.handle = handle;
        this.parameterHandles = parameterHandles;
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
        inUse = true;
    }

    /**
     * Sets a uniform four times four matrix value.
     *
     * @param parameterName The name of the parameter.
     * @param value The matrix to set.
     */
    public void setMatrix(String parameterName, Matrix4 value)
    {
        checkThatShaderIsInUse();
        FloatBuffer buffer = createFloatBuffer(NUMBER_OF_FLOATS);
        value.feed(buffer);
        buffer.flip();
        setMatrix(parameterName, buffer);
    }

    /**
     * Sets a uniform four times four matrix value.
     *
     * @param parameterName The name of the parameter.
     * @param value The matrix to set.
     */
    public void setMatrix(String parameterName, FloatBuffer value)
    {
        checkThatShaderIsInUse();
        int parameterHandle = requireNonNull(parameterHandles.get(parameterName), "No parameter with name '" + parameterName + "' was found");
        GL20.glUniformMatrix4fv(parameterHandle, false, value);
    }

    private void checkThatShaderIsInUse()
    {
        if (!inUse)
        {
            throw new IllegalStateException("The shader is not in use, and can therefor not have parameters set");
        }
    }

    /**
     * Stops using the shader program.
     */
    public void end()
    {
        GL20.glUseProgram(0);
        inUse = false;
    }
}
