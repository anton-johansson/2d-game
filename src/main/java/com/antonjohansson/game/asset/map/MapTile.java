package com.antonjohansson.game.asset.map;

/**
 * Defines a tile of a specific map part.
 */
public class MapTile
{
    private final TileSet tileset;
    private final int tileId;

    MapTile(TileSet tileset, int tileId)
    {
        this.tileset = tileset;
        this.tileId = tileId;
    }

    public TileSet getTileset()
    {
        return tileset;
    }

    public int getTileId()
    {
        return tileId;
    }
}
