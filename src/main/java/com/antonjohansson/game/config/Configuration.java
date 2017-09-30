package com.antonjohansson.game.config;

/**
 * Contains configuration for the game.
 */
public class Configuration
{
    private GraphicsConfiguration graphics = new GraphicsConfiguration();

    public GraphicsConfiguration getGraphics()
    {
        return graphics;
    }

    public void setGraphics(GraphicsConfiguration graphics)
    {
        this.graphics = graphics;
    }
}
