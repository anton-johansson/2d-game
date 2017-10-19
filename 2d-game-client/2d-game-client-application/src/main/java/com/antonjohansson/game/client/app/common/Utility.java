package com.antonjohansson.game.client.app.common;

/**
 * Defines utilities for the game.
 */
public final class Utility
{
    // Prevent instantiation
    private Utility()
    {
    }

    /**
     * Converts the given amount of pixels into in-game meters.
     *
     * @param pixels The number of pixels to convert to in-game meters.
     * @return Returns the meters.
     */
    public static float toMeters(int pixels)
    {
        float tiles = (float) pixels / Constants.TILE_SIZE;
        return tiles * Constants.METERS_PER_TILE;
    }

    /**
     * Converts the given amount of in-game meters into pixels.
     *
     * @param meters The meters to convert to pixels.
     * @return Returns the number of pixels.
     */
    public static int toPixels(float meters)
    {
        float tiles = meters / Constants.METERS_PER_TILE;
        return (int) (tiles * Constants.TILE_SIZE);
    }
}
