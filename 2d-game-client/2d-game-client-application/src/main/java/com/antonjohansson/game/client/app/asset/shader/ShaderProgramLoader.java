package com.antonjohansson.game.client.app.asset.shader;

import static com.antonjohansson.game.client.app.asset.map.MapPartLoader.MAPPER;
import static java.util.Collections.emptyList;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

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

            int handle = GL20.glCreateProgram();
            GL20.glAttachShader(handle, vertexShader.getHandle());
            GL20.glAttachShader(handle, fragmentShader.getHandle());

            for (ShaderProgramAttribute attribute : data.getAttributes())
            {
                GL20.glBindAttribLocation(handle, attribute.getIndex(), attribute.getName());
            }

            GL20.glLinkProgram(handle);
            if (GL20.glGetProgrami(handle, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
            {
                throw new RuntimeException("Could not link program: " + GL20.glGetProgramInfoLog(handle));
            }

            GL20.glValidateProgram(handle);
            if (GL20.glGetProgrami(handle, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
            {
                throw new RuntimeException("Could not validate program: " + GL20.glGetProgramInfoLog(handle));
            }

            Map<String, Integer> parameterHandles = new HashMap<>();
            for (String parameter : data.getParameters())
            {
                int parameterHandle = GL20.glGetUniformLocation(handle, parameter);
                parameterHandles.put(parameter, parameterHandle);
            }

            return new ShaderProgram(name, handle, parameterHandles, vertexShader, fragmentShader);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose(ShaderProgram asset, IAssetManager manager)
    {
        GL20.glDetachShader(asset.getHandle(), asset.getFragmentShader().getHandle());
        GL20.glDetachShader(asset.getHandle(), asset.getVertexShader().getHandle());
        manager.unsubscribe(asset.getFragmentShader());
        manager.unsubscribe(asset.getVertexShader());
        GL20.glDeleteProgram(asset.getHandle());
    }

    /**
     * Defines the data of the JSON-files, containing information about the shader programs.
     */
    static final class ShaderProgramData
    {
        private String vertexShader = "";
        private String fragmentShader = "";
        private List<String> parameters = emptyList();
        private List<ShaderProgramAttribute> attributes = emptyList();

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

        public List<String> getParameters()
        {
            return parameters;
        }

        public void setParameters(List<String> parameters)
        {
            this.parameters = parameters;
        }

        public List<ShaderProgramAttribute> getAttributes()
        {
            return attributes;
        }

        public void setAttributes(List<ShaderProgramAttribute> attributes)
        {
            this.attributes = attributes;
        }
    }

    /**
     * Defines attribute locations for the shader program.
     */
    static final class ShaderProgramAttribute
    {
        private int index;
        private String name = "";

        public int getIndex()
        {
            return index;
        }

        public void setIndex(int index)
        {
            this.index = index;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }
}
