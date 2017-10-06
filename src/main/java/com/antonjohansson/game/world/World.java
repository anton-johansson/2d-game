package com.antonjohansson.game.world;

import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.asset.IAssetManager;
import com.antonjohansson.game.asset.input.InputManager;
import com.antonjohansson.game.asset.map.MapConstants;
import com.antonjohansson.game.asset.map.MapPart;
import com.antonjohansson.game.asset.map.MapPartIdentifier;
import com.antonjohansson.game.asset.map.MapTile;
import com.antonjohansson.game.asset.map.TileSet;
import com.antonjohansson.game.time.IGameTime;

/**
 * Represents the world.
 */
public class World
{
    private final Player player = new Player();
    private TileSet tileset;
    private MapPart mapPart;

    /**
     * Initializes the world.
     *
     * @param assetManager The asset manager.
     */
    public void initialize(IAssetManager assetManager)
    {
        tileset = assetManager.subscribe(TileSet.class, 1);
        mapPart = assetManager.subscribe(MapPart.class, MapPartIdentifier.of(50, -50));
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
        tileset.getTexture().bind();

        for (int y = 0; y < MapConstants.VERTICAL_TILES_PER_PART; y++)
        {
            for (int x = 0; x < MapConstants.HORIZONTAL_TILES_PER_PART; x++)
            {
                MapTile tile = mapPart.getTile(x, y);

                float texLeft = tile.getTextureCoordinateLeft();
                float texRight = tile.getTextureCoordinateRight();
                float texBottom = tile.getTextureCoordinateBottom();
                float texTop = tile.getTextureCoordinateTop();

                float left = x * 16.0F + player.getX() * 1000F;
                float right = x * 16.0F + 16.0F + player.getX() * 1000F;
                float bottom = y * 16.0F + player.getY() * 1000F;
                float top = y * 16.0F + 16.0F + player.getY() * 1000F;

                GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(texLeft, texTop);
                GL11.glVertex2f(left, top);
                GL11.glTexCoord2f(texRight, texTop);
                GL11.glVertex2f(right, top);
                GL11.glTexCoord2f(texRight, texBottom);
                GL11.glVertex2f(right, bottom);
                GL11.glTexCoord2f(texLeft, texBottom);
                GL11.glVertex2f(left, bottom);
                GL11.glEnd();
            }
        }
    }
}
