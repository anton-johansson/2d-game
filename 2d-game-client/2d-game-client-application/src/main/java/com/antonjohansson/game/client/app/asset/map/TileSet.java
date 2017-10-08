package com.antonjohansson.game.client.app.asset.map;

import com.antonjohansson.game.client.app.asset.common.IAsset;
import com.antonjohansson.game.client.app.asset.texture.Texture;
import com.antonjohansson.game.client.app.common.Constants;

/**
 * Defines a set of tiles that can be used to render a map.
 */
public class TileSet implements IAsset
{
    private final int tilesetId;
    private final Texture texture;

    TileSet(int tilesetId, Texture texture)
    {
        this.tilesetId = tilesetId;
        this.texture = texture;
    }

    @Override
    public Object getIdentifier()
    {
        return tilesetId;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public int getNumberOfTilesWide()
    {
        return texture.getWidth() / Constants.TILE_SIZE;
    }

    public int getNumberOfTilesHigh()
    {
        return texture.getHeight() / Constants.TILE_SIZE;
    }
}
