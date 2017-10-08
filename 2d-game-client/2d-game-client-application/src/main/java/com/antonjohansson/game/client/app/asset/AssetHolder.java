package com.antonjohansson.game.client.app.asset;

import com.antonjohansson.game.client.app.asset.common.IAsset;

/**
 * Holds an asset together with its subscriber count.
 *
 * @param <A> The type of the asset.
 */
public class AssetHolder<A extends IAsset>
{
    private final A asset;
    private int numberOfSubscribers;

    public AssetHolder(A asset)
    {
        this.asset = asset;
    }

    public A getAsset()
    {
        return asset;
    }

    /**
     * Indicates that a source is subscribing to this asset.
     */
    public void subscribe()
    {
        numberOfSubscribers++;
    }

    /**
     * Indicates that a source is unsubscribing from this asset.
     */
    public void unsubscribe()
    {
        numberOfSubscribers--;
    }

    /**
     * Gets whether or not this asset has any subscribers.
     *
     * @return Returns {@code true} if this asset has subscribers.
     */
    public boolean isSubscribedTo()
    {
        return numberOfSubscribers > 0;
    }
}
