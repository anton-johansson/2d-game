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

    private int index()
    {
        return tileId - 1;
    }

    public int getTileX()
    {
        return index() % tileset.getNumberOfTilesWide();
    }

    public int getTileY()
    {
        return tileset.getNumberOfTilesHigh() - index() / tileset.getNumberOfTilesHigh();
    }

    public float getTextureCoordinateLeft()
    {
        return (float) getTileX() / (float) tileset.getNumberOfTilesWide();
    }

    public float getTextureCoordinateRight()
    {
        return (float) (getTileX() + 1) / (float) tileset.getNumberOfTilesWide();
    }

    public float getTextureCoordinateBottom()
    {
        return (float) (getTileY() - 1) / (float) tileset.getNumberOfTilesHigh();
    }

    public float getTextureCoordinateTop()
    {
        return (float) getTileY() / (float) tileset.getNumberOfTilesHigh();
    }
}
