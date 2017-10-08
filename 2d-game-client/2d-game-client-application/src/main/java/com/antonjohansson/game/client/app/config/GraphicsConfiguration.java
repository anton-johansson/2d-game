package com.antonjohansson.game.client.app.config;

import java.util.OptionalInt;

/**
 * Contains graphics configuration for the game.
 */
public class GraphicsConfiguration
{
    private int width;
    private int height;
    private boolean verticalSyncEnabled;
    private int frameCap;

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public boolean isVerticalSyncEnabled()
    {
        return verticalSyncEnabled;
    }

    public void setVerticalSyncEnabled(boolean verticalSyncEnabled)
    {
        this.verticalSyncEnabled = verticalSyncEnabled;
    }

    public void setFrameCap(int frameCap)
    {
        this.frameCap = frameCap;
    }

    public OptionalInt getFrameCap()
    {
        return frameCap > 0 ? OptionalInt.of(frameCap) : OptionalInt.empty();
    }
}
