package com.antonjohansson.game.client.app.asset.shader;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.function.BiFunction;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.antonjohansson.game.client.app.asset.IAssetLoader;
import com.antonjohansson.game.client.app.asset.IAssetManager;

/**
 * Abstract skeleton for loading shaders.
 *
 * @param <S> The type of the shader.
 */
abstract class AbstractShaderLoader<S extends AbstractShader> implements IAssetLoader<S, String>
{
    private final String extension;
    private final int shaderType;
    private final BiFunction<Integer, String, S> constructor;
    private String shaderLocation;

    AbstractShaderLoader(String extension, int shaderType, BiFunction<Integer, String, S> constructor)
    {
        this.extension = extension;
        this.shaderType = shaderType;
        this.constructor = constructor;
    }

    @Override
    public final void setAssetLocation(String assetLocation)
    {
        this.shaderLocation = assetLocation + "shaders/";
    }

    @Override
    public final Class<String> getIdentifierType()
    {
        return String.class;
    }

    @Override
    public final S load(String identifier, IAssetManager manager)
    {
        String fileName = shaderLocation + identifier + extension;
        File file = new File(fileName);
        String shaderSource = getShaderSource(file);
        int handle = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(handle, shaderSource);
        GL20.glCompileShader(handle);
        if (GL20.glGetShaderi(handle, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        {
            throw new RuntimeException("Could not compile shader with name '" + identifier + "': " + GL20.glGetShaderInfoLog(handle));
        }
        return constructor.apply(handle, identifier);
    }

    private String getShaderSource(File file)
    {
        try
        {
            return readFileToString(file, "UTF-8");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public final void dispose(S asset, IAssetManager manager)
    {
        GL20.glDeleteShader(asset.getHandle());
    }
}
