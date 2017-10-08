package com.antonjohansson.game.client.app.asset.map;

import com.antonjohansson.game.client.app.asset.IAssetLoader;
import com.antonjohansson.game.client.app.asset.IAssetManager;
import com.antonjohansson.game.client.app.asset.texture.Texture;

/**
 * Loads {@link TileSet tile sets}.
 */
public class TilesetLoader implements IAssetLoader<TileSet, Integer>
{
    @Override
    public void setAssetLocation(String assetLocation)
    {
    }

    @Override
    public Class<Integer> getIdentifierType()
    {
        return Integer.class;
    }

    @Override
    public TileSet load(Integer tilesetId, IAssetManager manager)
    {
        String textureName = String.format("tilesets/tileset%03d.png", tilesetId);
        Texture texture = manager.subscribe(Texture.class, textureName);
        return new TileSet(tilesetId, texture);
    }

    @Override
    public void dispose(TileSet tileset, IAssetManager manager)
    {
        manager.unsubscribe(tileset.getTexture());
    }
}
