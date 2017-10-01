package com.antonjohansson.game.asset;

import org.lwjgl.opengl.GL11;

/**
 * Defines a texture that can be rendered.
 */
public class Texture implements IAsset
{
    private final int identifier;
    private final int width;
    private final int height;

    Texture(int identifier, int width, int height)
    {
        this.identifier = identifier;
        this.width = width;
        this.height = height;
    }

    public int getIdentifier()
    {
        return identifier;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    /**
     * Binds the texture to the graphics renderer.
     */
    public void bind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, identifier);
    }

    @Override
    public void dispose()
    {
        GL11.glDeleteTextures(identifier);
    }
}
