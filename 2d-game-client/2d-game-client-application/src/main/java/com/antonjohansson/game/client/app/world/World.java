package com.antonjohansson.game.client.app.world;

import com.antonjohansson.game.client.app.asset.IAssetManager;
import com.antonjohansson.game.client.app.asset.input.InputManager;
import com.antonjohansson.game.client.app.asset.map.MapPart;
import com.antonjohansson.game.client.app.asset.map.MapPartIdentifier;
import com.antonjohansson.game.client.app.asset.map.TileSet;
import com.antonjohansson.game.client.app.config.Configuration;
import com.antonjohansson.game.client.app.time.IGameTime;

/**
 * Represents the world.
 */
public class World
{
    private final Player player = new Player();
    private final MapManager mapManager;
    private TileSet tileset;
    private MapPart mapPart;

    public World(IAssetManager assetManager, Configuration configuration)
    {
        mapManager = new MapManager(assetManager, configuration, player);
    }

    /**
     * Initializes the world.
     *
     * @param assetManager The asset manager.
     */
    public void initialize(IAssetManager assetManager)
    {
        tileset = assetManager.subscribe(TileSet.class, 1);
        mapPart = assetManager.subscribe(MapPart.class, MapPartIdentifier.of(50, -50));
        mapManager.initialize();
    }

    /**
     * Cleans up the world.
     *
     * @param assetManager The asset manager.
     */
    public void dispose(IAssetManager assetManager)
    {
        assetManager.unsubscribe(tileset);
        assetManager.unsubscribe(mapPart);
        mapManager.dispose();
    }

    /**
     * Updates the world.
     *
     * @param gameTime The current game time.
     * @param inputManager The input manager.
     */
    public void update(IGameTime gameTime, InputManager inputManager)
    {
        player.update(gameTime, inputManager);
    }

    /**
     * Renders the world.
     */
    public void render()
    {
        mapManager.render();
    }
}
