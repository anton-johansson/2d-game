package com.antonjohansson.game.asset;

import static java.util.Objects.requireNonNull;

import com.antonjohansson.game.asset.common.IAsset;

/**
 * Provides operations for managing assets.
 */
public interface IAssetManager
{
    /**
     * Subscribes to an asset of a given type and a given identifier. If the asset is not loaded, it will be loaded.
     *
     * @param type The type of the asset to subscribe to.
     * @param identifier The identifier of the asset to subscribe to.
     * @return Returns the subscribed asset..
     */
    <A extends IAsset> A subscribe(Class<A> type, Object identifier);

    /**
     * Unsubscribes from a given asset, causing it to be disposed of if no other service is subscribing to it.
     *
     * @param type The type of the asset to unsubscribe from.
     * @param identifier The identifier of the asset to unsubscribe from.
     */
    <A extends IAsset> void unsubscribe(Class<A> type, Object identifier);

    /**
     * Unsubscribes from a given asset, causing it to be disposed of if no other service is subscribing to it.
     *
     * @param asset The asset to unsubscribe from.
     */
    default <A extends IAsset> void unsubscribe(A asset)
    {
        Class<? extends IAsset> type = requireNonNull(asset, "The asset cannot be null").getClass();
        Object identifier = asset.getIdentifier();
        unsubscribe(type, identifier);
    }
}
