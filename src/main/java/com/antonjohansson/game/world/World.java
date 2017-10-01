package com.antonjohansson.game.world;

import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.asset.AssetManager;
import com.antonjohansson.game.asset.Texture;
import com.antonjohansson.game.time.GameTime;

/**
 * Represents the world.
 */
public class World
{
    private Texture tileset01;

    /**
     * Initializes the world.
     *
     * @param assetManager The asset manager.
     */
    public void initialize(AssetManager assetManager)
    {
        tileset01 = assetManager.getAsset(Texture.class, "tileset01.png");
    }

    /**
     * Cleans up the world.
     */
    public void dispose()
    {
        tileset01.dispose();
    }

    /**
     * Updates the world.
     *
     * @param gameTime The current game time.
     */
    public void update(GameTime gameTime)
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
        GL11.glVertex2f(-0.5F, 0.5F);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glVertex2f(0.5F, 0.5F);
        GL11.glTexCoord2f(1.0F, 0.0F);
        GL11.glVertex2f(0.5F, -0.5F);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex2f(-0.5F, -0.5F);
        GL11.glEnd();
    }
}
