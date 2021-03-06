package com.antonjohansson.game.client.app.common;

/**
 * Provides constants for the game.
 */
public final class Constants
{
    /** Defines the width and height in pixels of a single tile. */
    public static final int TILE_SIZE = 16;

    /** The number of real-world meters horizontally that a map part spans. */
    public static final int HORIZONTAL_METERS_PER_MAP_PART = 30;

    /** The number of real-world meters vertically that a map part spans. */
    public static final int VERTICAL_METERS_PER_MAP_PART = 25;

    /** The number of real-world meters per single tile. */
    public static final float METERS_PER_TILE = 0.25F;

    /** The number of tiles horizontally per map part. */
    public static final int HORIZONTAL_TILES_PER_PART = (int) (HORIZONTAL_METERS_PER_MAP_PART / METERS_PER_TILE);

    /** The number of tiles vertically per map part. */
    public static final int VERTICAL_TILES_PER_PART = (int) (VERTICAL_METERS_PER_MAP_PART / METERS_PER_TILE);

    /** The width of a whole map part in pixels. */
    public static final int MAP_PART_WIDTH_IN_PIXELS = HORIZONTAL_TILES_PER_PART * TILE_SIZE;

    /** The height of a whole map part in pixels. */
    public static final int MAP_PART_HEIGHT_IN_PIXELS = VERTICAL_TILES_PER_PART * TILE_SIZE;

    // Prevent instantiation
    private Constants()
    {
    }
}
