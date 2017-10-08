package com.antonjohansson.game.client.app.asset.map.raw;

/**
 * Defines a tile in the raw map data structure.
 */
public class MapDataTile
{
    private int tilesetId;
    private int tileId;

    public int getTilesetId()
    {
        return tilesetId;
    }

    public void setTilesetId(int tilesetId)
    {
        this.tilesetId = tilesetId;
    }

    public int getTileId()
    {
        return tileId;
    }

    public void setTileId(int tileId)
    {
        this.tileId = tileId;
    }
}
