package com.antonjohansson.game.world;

import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.asset.IAssetManager;
import com.antonjohansson.game.asset.Texture;
import com.antonjohansson.game.asset.map.MapPart;
import com.antonjohansson.game.asset.map.MapPartIdentifier;
import com.antonjohansson.game.time.IGameTime;

/**
 * Represents the world.
 */
public class World
{
    private Texture tileset01;
    private MapPart mapPart;

    /**
     * Initializes the world.
     *
     * @param assetManager The asset manager.
     */
    public void initialize(IAssetManager assetManager)
    {
        tileset01 = assetManager.subscribe(Texture.class, "tileset01.png");
        mapPart = assetManager.subscribe(MapPart.class, MapPartIdentifier.of(50, -50));
    }

    /**
     * Cleans up the world.
     *
     * @param assetManager The asset manager.
     */
    public void dispose(IAssetManager assetManager)
    {
        assetManager.unsubscribe(tileset01);
        assetManager.unsubscribe(mapPart);
    }

    /**
     * Updates the world.
     *
     * @param gameTime The current game time.
     */
    public void update(IGameTime gameTime)
    {
    }

    /**
     * Renders the world.
     */
    public void render()
    {
        tileset01.bind();

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex2f(0.0F, 600.0F);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(800.0F, 600.0F);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(800.0F, 0.0F);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(0.0F, 0.0F);
        GL11.glEnd();
    }
}
