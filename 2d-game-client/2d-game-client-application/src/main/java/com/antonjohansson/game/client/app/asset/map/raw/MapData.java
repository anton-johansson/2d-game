package com.antonjohansson.game.client.app.asset.map.raw;

/**
 * Contains data about a map part in a raw format.
 */
public class MapData
{
    private MapDataTile[][] tiles;

    public MapDataTile[][] getTiles()
    {
        return tiles;
    }

    public void setTiles(MapDataTile[][] tiles)
    {
        this.tiles = tiles;
    }
}
