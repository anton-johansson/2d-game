package com.antonjohansson.game.client.app.asset.shader;

import static com.antonjohansson.game.client.app.asset.map.MapPartLoader.MAPPER;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.client.app.asset.IAssetLoader;
import com.antonjohansson.game.client.app.asset.IAssetManager;

/**
 * Loads {@link ShaderProgram shader programs}.
 */
public class ShaderProgramLoader implements IAssetLoader<ShaderProgram, String>
{
    private String shaderLocation;

    @Override
    public void setAssetLocation(String assetLocation)
    {
        this.shaderLocation = assetLocation + "shaders/";
    }

    @Override
    public Class<String> getIdentifierType()
    {
        return String.class;
    }

    @Override
    public ShaderProgram load(String name, IAssetManager manager)
    {
        String fileName = shaderLocation + name + ".json";

        File file = new File(fileName);
        try (Reader reader = new FileReader(file))
        {
            ShaderProgramData data = MAPPER.fromJson(reader, ShaderProgramData.class);
            VertexShader vertexShader = manager.subscribe(VertexShader.class, data.getVertexShader());
            FragmentShader fragmentShader = manager.subscribe(FragmentShader.class, data.getFragmentShader());

            int handle = ARBShaderObjects.glCreateProgramObjectARB();
            ARBShaderObjects.glAttachObjectARB(handle, vertexShader.getHandle());
            ARBShaderObjects.glAttachObjectARB(handle, fragmentShader.getHandle());

            ARBShaderObjects.glLinkProgramARB(handle);
            if (ARBShaderObjects.glGetObjectParameteriARB(handle, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE)
            {
                throw new RuntimeException("could not link program: " + getLogInfo(handle));
            }

            ARBShaderObjects.glValidateProgramARB(handle);
            if (ARBShaderObjects.glGetObjectParameteriARB(handle, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE)
            {
                throw new RuntimeException("could not validate program: " + getLogInfo(handle));
            }

            return new ShaderProgram(name, handle, vertexShader, fragmentShader);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose(ShaderProgram asset, IAssetManager manager)
    {
        manager.unsubscribe(asset.getFragmentShader());
        manager.unsubscribe(asset.getVertexShader());
        ARBShaderObjects.glDeleteObjectARB(asset.getHandle());
    }

    private String getLogInfo(int obj)
    {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }

    /**
     * Defines the data of the JSON-files, containing information about the shader programs.
     */
    static final class ShaderProgramData
    {
        private String vertexShader = "";
        private String fragmentShader = "";

        public String getVertexShader()
        {
            return vertexShader;
        }

        public void setVertexShader(String vertexShader)
        {
            this.vertexShader = vertexShader;
        }

        public String getFragmentShader()
        {
            return fragmentShader;
        }

        public void setFragmentShader(String fragmentShader)
        {
            this.fragmentShader = fragmentShader;
        }
    }
}
