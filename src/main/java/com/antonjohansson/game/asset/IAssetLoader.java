package com.antonjohansson.game.asset;

import com.antonjohansson.game.asset.common.IAsset;

/**
 * In charge of loading a specific type of asset.
 *
 * @param <A> The type of the resource that this asset loads.
 * @param <I> The type of the identifier used to load resources.
 */
public interface IAssetLoader<A extends IAsset, I>
{
    /**
     * Sets the location of the assets.
     *
     * @param assetLocation The location.
     */
    void setAssetLocation(String assetLocation);

    /**
     * Gets the type used as identifier for this type of asset.
     *
     * @return Returns the identifier type.
     */
    Class<I> getIdentifierType();

    /**
     * Loads an asset by its identifier.
     *
     * @param identifier The identifier of the asset.
     * @return Returns the asset.
     */
    A load(I identifier);

    /**
     * Disposes the asset.
     *
     * @param asset The asset to dispose of.
     */
    void dispose(A asset);
}
