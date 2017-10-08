package com.antonjohansson.game.client.app.asset.map;

import com.antonjohansson.game.client.app.asset.common.IAsset;

/**
 * Defines a small part of the map.
 */
public class MapPart implements IAsset
{
    private final MapPartIdentifier identifier;
    private final MapTile[][] tiles;

    MapPart(MapPartIdentifier identifier, MapTile[][] tiles)
    {
        this.identifier = identifier;
        this.tiles = tiles;
    }

    @Override
    public MapPartIdentifier getIdentifier()
    {
        return identifier;
    }

    /**
     * Gets the tile of the given coordinate within the part.
     *
     * @param x The horizontal coordinate (x) of the part.
     * @param y The vertical coordinate (y) of the part.
     * @return Returns the tile at the specified coordinate.
     */
    public MapTile getTile(int x, int y)
    {
        return tiles[x][y];
    }
}
