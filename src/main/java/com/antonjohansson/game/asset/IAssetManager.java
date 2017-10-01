package com.antonjohansson.game.asset;

/**
 * Provides operations for managing assets.
 */
public interface IAssetManager
{
    /**
     * Initializes the asset manager.
     */
    void initialize();

    /**
     * Gets an asset of a given type by its name.
     *
     * @param type The type of the asset.
     * @param name The name of the asset.
     * @return Returns the loaded asset.
     */
    <R extends IAsset> R getAsset(Class<R> type, String name);
}
