package com.antonjohansson.game.client.app.asset.shader;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.function.BiFunction;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

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
        int handle = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
        ARBShaderObjects.glShaderSourceARB(handle, shaderSource);
        ARBShaderObjects.glCompileShaderARB(handle);
        if (ARBShaderObjects.glGetObjectParameteriARB(handle, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
        {
            throw new RuntimeException("Could not compile shader with name '" + identifier + "': " + getLogInfo(handle));
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

    private String getLogInfo(int obj)
    {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    @Override
    public final void dispose(S asset, IAssetManager manager)
    {
        ARBShaderObjects.glDeleteObjectARB(asset.getHandle());
    }
}
