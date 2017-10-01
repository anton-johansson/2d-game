package com.antonjohansson.game.asset;

/**
 * In charge of loading a specific type of asset.
 *
 * @param <A> The type of the resource that this asset loads.
 */
public interface IAssetLoader<A extends IAsset>
{
    /**
     * Sets the location of the assets.
     *
     * @param assetLocation The location.
     */
    void setAssetLocation(String assetLocation);

    /**
     * Loads an asset by its name.
     *
     * @param name The name of the asset.
     * @return Returns the asset.
     */
    A load(String name);
}
