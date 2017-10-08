package com.antonjohansson.game.world;

import com.antonjohansson.game.asset.IAssetManager;

/**
 * Manages map-parts and makes sure that the proper parts of the maps are loaded and rendered on the correct location.
 */
public class MapManager
{
    private final IAssetManager assetManager;
    private final Player player;

    public MapManager(IAssetManager assetManager, Player player)
    {
        this.assetManager = assetManager;
        this.player = player;
    }

    /**
     * Initializes the map manager and loads the initial state of the map.
     */
    public void initialize()
    {
        float x = player.getX();
        float y = player.getY();
    }
}
