package com.antonjohansson.game.asset.map;

import static com.antonjohansson.game.asset.map.MapConstants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.asset.map.MapConstants.VERTICAL_TILES_PER_PART;

import com.antonjohansson.game.asset.common.IAsset;

/**
 * Defines a small part of the map.
 */
public class MapPart implements IAsset
{
    private MapTile[][] tiles = new MapTile[HORIZONTAL_TILES_PER_PART][VERTICAL_TILES_PER_PART];

    public MapPart(MapTile[][] tiles)
    {
        this.tiles = tiles;
    }

    @Override
    public void dispose()
    {
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
