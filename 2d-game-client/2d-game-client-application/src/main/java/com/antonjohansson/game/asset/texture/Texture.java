package com.antonjohansson.game.asset.texture;

import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.asset.common.IAsset;

/**
 * Defines a texture that can be rendered.
 */
public class Texture implements IAsset
{
    private final String name;
    private final int graphicsIdentifier;
    private final int width;
    private final int height;

    Texture(String name, int graphicsIdentifier, int width, int height)
    {
        this.name = name;
        this.graphicsIdentifier = graphicsIdentifier;
        this.width = width;
        this.height = height;
    }

    int getGraphicsIdentifier()
    {
        return graphicsIdentifier;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public String getIdentifier()
    {
        return name;
    }

    /**
     * Binds the texture to the graphics renderer.
     */
    public void bind()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, graphicsIdentifier);
    }
}
