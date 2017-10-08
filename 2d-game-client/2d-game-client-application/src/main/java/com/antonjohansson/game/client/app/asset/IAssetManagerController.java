package com.antonjohansson.game.client.app.asset;

/**
 * Extension of {@link IAssetManager} that can also control the manager itself.
 */
public interface IAssetManagerController extends IAssetManager
{
    /**
     * Initializes the asset manager.
     */
    void initialize();

    /**
     * Disposes the asset manager.
     */
    void dispose();
}
