package com.antonjohansson.game.client.app.world;

import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_METERS_PER_MAP_PART;
import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.client.app.common.Constants.MAP_PART_HEIGHT_IN_PIXELS;
import static com.antonjohansson.game.client.app.common.Constants.MAP_PART_WIDTH_IN_PIXELS;
import static com.antonjohansson.game.client.app.common.Constants.TILE_SIZE;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_METERS_PER_MAP_PART;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_TILES_PER_PART;

import org.lwjgl.opengl.GL11;

import com.antonjohansson.game.client.app.asset.IAssetManager;
import com.antonjohansson.game.client.app.asset.map.MapPart;
import com.antonjohansson.game.client.app.asset.map.MapPartIdentifier;
import com.antonjohansson.game.client.app.asset.map.MapTile;
import com.antonjohansson.game.client.app.common.Constants;
import com.antonjohansson.game.client.app.config.Configuration;

/**
 * Manages map-parts and makes sure that the proper parts of the maps are loaded and rendered on the correct location.
 */
public class MapManager
{
    private static final int HORIZONTALLY_SIMULTANEOUS_PARTS = 4;
    private static final int VERTICALLY_SIMULTANEOUS_PARTS = 4;
    private static final float HALF = 0.5F;

    private final MapPart[][] loadedParts = new MapPart[HORIZONTALLY_SIMULTANEOUS_PARTS][VERTICALLY_SIMULTANEOUS_PARTS];
    private final IAssetManager assetManager;
    private final Configuration configuration;
    private final Player player;

    public MapManager(IAssetManager assetManager, Configuration configuration, Player player)
    {
        this.assetManager = assetManager;
        this.configuration = configuration;
        this.player = player;
    }

    /**
     * Initializes the map manager and loads the initial state of the map.
     */
    public void initialize()
    {
        float worldX = player.getX();
        float worldY = player.getY();

        int playerPartX = (int) (worldX / HORIZONTAL_METERS_PER_MAP_PART);
        int playerPartY = (int) (worldY / VERTICAL_METERS_PER_MAP_PART);

        boolean moreEast = worldX % HORIZONTAL_METERS_PER_MAP_PART / HORIZONTAL_METERS_PER_MAP_PART > HALF;
        boolean moreNorth = worldY % VERTICAL_METERS_PER_MAP_PART / VERTICAL_METERS_PER_MAP_PART > HALF;

        int westPart = playerPartX - (moreEast ? 1 : 2);
        int eastPart = westPart + HORIZONTALLY_SIMULTANEOUS_PARTS - 1;
        int southPart = playerPartY - (moreNorth ? 1 : 2);
        int northPart = southPart + VERTICALLY_SIMULTANEOUS_PARTS - 1;

        for (int partY = southPart, y = 0; partY <= northPart; partY++, y++)
        {
            for (int partX = westPart, x = 0; partX <= eastPart; partX++, x++)
            {
                MapPartIdentifier identifier = MapPartIdentifier.of(partX, partY);
                System.out.println("Loading map part " + identifier);
                MapPart part = assetManager.subscribe(MapPart.class, identifier);
                loadedParts[x][y] = part;
            }
        }
    }

    /**
     * Renders the visible parts of the map.
     */
    public void render()
    {
        GL11.glBegin(GL11.GL_QUADS);
        for (int y = 0; y < VERTICALLY_SIMULTANEOUS_PARTS; y++)
        {
            for (int x = 0; x < HORIZONTALLY_SIMULTANEOUS_PARTS; x++)
            {
                MapPart part = loadedParts[x][y];
                renderPart(part);
            }
        }
        GL11.glEnd();
    }

    private void renderPart(MapPart part)
    {
        int partLeft = part.getX() * MAP_PART_WIDTH_IN_PIXELS;
        int partRight = part.getX() * MAP_PART_WIDTH_IN_PIXELS + MAP_PART_WIDTH_IN_PIXELS;
        int partBottom = part.getY() * MAP_PART_HEIGHT_IN_PIXELS;
        int partTop = part.getY() * MAP_PART_HEIGHT_IN_PIXELS + MAP_PART_HEIGHT_IN_PIXELS;

        if (!isInBounds(partLeft, partRight, partBottom, partTop))
        {
            return;
        }

        int partX = partLeft - playerX();
        int partY = partBottom - playerY();

        int screenDeltaX = screenDeltaX();
        int screenDeltaY = screenDeltaY();

        for (int y = 0; y < VERTICAL_TILES_PER_PART; y++)
        {
            for (int x = 0; x < HORIZONTAL_TILES_PER_PART; x++)
            {
                MapTile tile = part.getTile(x, y);
                tile.getTileset().getTexture().bind();

                float texLeft = tile.getTextureCoordinateLeft();
                float texRight = tile.getTextureCoordinateRight();
                float texBottom = tile.getTextureCoordinateBottom();
                float texTop = tile.getTextureCoordinateTop();

                int left = partX + x * TILE_SIZE + screenDeltaX;
                int right = partX + x * TILE_SIZE + TILE_SIZE + screenDeltaX;
                int bottom = partY + y * TILE_SIZE + screenDeltaY;
                int top = partY + y * TILE_SIZE + TILE_SIZE + screenDeltaY;

                if (!isInBounds(left, right, bottom, top))
                {
                    continue;
                }

                GL11.glTexCoord2f(texLeft, texTop);
                GL11.glVertex2f(left, top);
                GL11.glTexCoord2f(texRight, texTop);
                GL11.glVertex2f(right, top);
                GL11.glTexCoord2f(texRight, texBottom);
                GL11.glVertex2f(right, bottom);
                GL11.glTexCoord2f(texLeft, texBottom);
                GL11.glVertex2f(left, bottom);
            }
        }
    }

    private boolean isInBounds(int left, int right, int bottom, int top)
    {
        return left <= configuration.getGraphics().getWidth()
            && right >= 0.0F
            && bottom <= configuration.getGraphics().getHeight()
            && top >= 0.0F;
    }

    /** The players absolute X-coordinate in pixels. */
    private int playerX()
    {
        return (int) (player.getX() / Constants.METERS_PER_TILE * Constants.TILE_SIZE);
    }

    /** The players absolute Y-coordinate in pixels. */
    private int playerY()
    {
        return (int) (player.getY() / Constants.METERS_PER_TILE * Constants.TILE_SIZE);
    }

    private int screenDeltaX()
    {
        return configuration.getGraphics().getWidth() / 2;
    }

    private int screenDeltaY()
    {
        return configuration.getGraphics().getHeight() / 2;
    }

    /**
     * Disposes all the loaded parts of the map.
     */
    public void dispose()
    {
        for (int y = 0; y < VERTICALLY_SIMULTANEOUS_PARTS; y++)
        {
            for (int x = 0; x < HORIZONTALLY_SIMULTANEOUS_PARTS; x++)
            {
                MapPart part = loadedParts[x][y];
                assetManager.unsubscribe(part);
            }
        }
    }
}
