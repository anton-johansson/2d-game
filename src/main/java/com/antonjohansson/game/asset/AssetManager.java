package com.antonjohansson.game.asset;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link IAssetManager}.
 */
public class AssetManager implements IAssetManager
{
    private final Map<Class<? extends IAsset>, IAssetLoader<? extends IAsset>> loaders = new HashMap<>();

    public AssetManager()
    {
        loaders.put(Texture.class, new TextureLoader());
    }

    @Override
    public void initialize()
    {
        String assetLocation = System.getProperty("assetLocation");
        if (!isValid(assetLocation))
        {
            throw new RuntimeException("The given asset location is invalid");
        }
        loaders.values().forEach(loader -> loader.setAssetLocation(assetLocation));
    }

    private boolean isValid(String assetLocation)
    {
        if (assetLocation == null)
        {
            return false;
        }
        File directory = new File(assetLocation);
        return directory.exists()
            && directory.isDirectory();
    }

    @Override
    public <A extends IAsset> A getAsset(Class<A> type, String name)
    {
        return loader(type).load(name);
    }

    @SuppressWarnings("unchecked")
    private <A extends IAsset> IAssetLoader<A> loader(Class<A> type)
    {
        IAssetLoader<A> loader = (IAssetLoader<A>) loaders.get(type);
        return loader;
    }
}
