package com.antonjohansson.game.asset.map;

/**
 * Defines a tile of a specific map part.
 */
public class MapTile
{
    private final int tilesetId;
    private final int tileId;

    MapTile(int tilesetId, int tileId)
    {
        this.tilesetId = tilesetId;
        this.tileId = tileId;
    }

    public int getTilesetId()
    {
        return tilesetId;
    }

    public int getTileId()
    {
        return tileId;
    }
}
