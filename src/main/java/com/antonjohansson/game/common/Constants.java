package com.antonjohansson.game.common;

/**
 * Provides constants for the game.
 */
public final class Constants
{
    /** Defines the width and height in pixels of a single tile. */
    public static final int TILE_SIZE = 16;

    /** The number of tiles horizontally per map part. */
    public static final int HORIZONTAL_TILES_PER_PART = 16;

    /** The number of tiles vertically per map part. */
    public static final int VERTICAL_TILES_PER_PART = 16;

    /** The number of real-world meters per single tile. */
    public static final float METER_PER_TILE = 0.25F;

    // Prevent instantiation
    private Constants()
    {
    }
}
