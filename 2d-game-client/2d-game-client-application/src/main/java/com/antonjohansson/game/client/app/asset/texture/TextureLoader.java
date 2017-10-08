package com.antonjohansson.game.client.app.asset.texture;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import com.antonjohansson.game.client.app.asset.IAssetLoader;
import com.antonjohansson.game.client.app.asset.IAssetManager;

/**
 * Loads {@link Texture textures}.
 */
public class TextureLoader implements IAssetLoader<Texture, String>
{
    private static final int CHANNELS = 4;

    private String textureLocation;

    @Override
    public void setAssetLocation(String assetLocation)
    {
        this.textureLocation = assetLocation + "textures/";
    }

    @Override
    public Class<String> getIdentifierType()
    {
        return String.class;
    }

    @Override
    public Texture load(String name, IAssetManager manager)
    {
        try (MemoryStack stack = MemoryStack.stackPush(); InputStream stream = TextureLoader.class.getResourceAsStream("/textures/" + name))
        {
            String path = textureLocation + name;

            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            IntBuffer component = stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            ByteBuffer data = STBImage.stbi_load(path, x, y, component, CHANNELS);
            if (data == null)
            {
                throw new RuntimeException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            int width = x.get();
            int height = y.get();

            int identifier = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, identifier);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

            Texture texture = new Texture(name, identifier, width, height);
            return texture;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose(Texture asset, IAssetManager manager)
    {
        GL11.glDeleteTextures(asset.getGraphicsIdentifier());
    }
}
