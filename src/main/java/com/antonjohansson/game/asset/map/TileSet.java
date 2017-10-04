package com.antonjohansson.game.asset.map;

import com.antonjohansson.game.asset.common.IAsset;
import com.antonjohansson.game.asset.texture.Texture;

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
}
