package com.antonjohansson.game.client.app.world;

import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_METERS_PER_MAP_PART;
import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.client.app.common.Constants.METERS_PER_TILE;
import static com.antonjohansson.game.client.app.common.Constants.TILE_SIZE;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_METERS_PER_MAP_PART;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_TILES_PER_PART;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL13;

import com.antonjohansson.game.client.app.asset.IAssetManager;
import com.antonjohansson.game.client.app.asset.map.MapPart;
import com.antonjohansson.game.client.app.asset.map.MapPartIdentifier;
import com.antonjohansson.game.client.app.asset.map.MapTile;
import com.antonjohansson.game.client.app.asset.shader.ShaderProgram;
import com.antonjohansson.game.client.app.asset.texture.Texture;
import com.antonjohansson.game.client.app.common.Constants;
import com.antonjohansson.game.client.app.common.Utility;
import com.antonjohansson.game.client.app.config.Configuration;
import com.antonjohansson.game.client.app.rendering.GraphicBuffer;
import com.antonjohansson.game.client.app.rendering.TexturedVertex;
import com.antonjohansson.game.client.math.Matrix4;

/**
 * Manages map-parts and makes sure that the proper parts of the maps are loaded and rendered on the correct location.
 */
public class MapManager
{
    private static final int HORIZONTALLY_SIMULTANEOUS_PARTS = 4;
    private static final int VERTICALLY_SIMULTANEOUS_PARTS = 4;
    private static final int NUMBER_OF_VERTICES_PER_TILE = 4;
    private static final int NUMBER_OF_INDICES_PER_TILE = 6;
    private static final float HALF = 0.5F;

    private final MapPart[][] loadedParts = new MapPart[HORIZONTALLY_SIMULTANEOUS_PARTS][VERTICALLY_SIMULTANEOUS_PARTS];
    private final Map<MapPartIdentifier, MapPart> loadedPartsByIdentifier = new HashMap<>();
    private final IAssetManager assetManager;
    private final Configuration configuration;
    private final Player player;
    private GraphicBuffer<TexturedVertex> buffer;
    private ShaderProgram shader;
    private Texture tileset001;
    private TexturedVertex[] vertices;

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
        shader = assetManager.subscribe(ShaderProgram.class, "map");
        tileset001 = assetManager.subscribe(Texture.class, "tilesets/tileset001.png");

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
                loadedPartsByIdentifier.put(identifier, part);
            }
        }

        int numberOfTilesWide = numberOfTilesWide();
        int numberOfTilesHigh = numberOfTilesHigh();

        vertices = new TexturedVertex[numberOfTilesWide * numberOfTilesHigh * NUMBER_OF_VERTICES_PER_TILE];
        for (int index = 0; index < vertices.length; index++)
        {
            vertices[index] = new TexturedVertex();
        }

        int[] indices = new int[numberOfTilesWide * numberOfTilesHigh * NUMBER_OF_INDICES_PER_TILE];
        for (int y = 0; y < numberOfTilesHigh; y++)
        {
            for (int x = 0; x < numberOfTilesWide; x++)
            {
                int index = (x + y * numberOfTilesWide) * NUMBER_OF_INDICES_PER_TILE;
                int startingPositionForTile = y * numberOfTilesWide * NUMBER_OF_VERTICES_PER_TILE + x * NUMBER_OF_VERTICES_PER_TILE;

                int topLeft = startingPositionForTile;
                int bottomLeft = startingPositionForTile + 1;
                int bottomRight = startingPositionForTile + 2;
                int topRight = startingPositionForTile + 3;

                // CSOFF
                indices[index + 0] = topLeft;
                indices[index + 1] = bottomLeft;
                indices[index + 2] = bottomRight;
                indices[index + 3] = bottomRight;
                indices[index + 4] = topRight;
                indices[index + 5] = topLeft;
                // CSON
            }
        }

        buffer = GraphicBuffer.of(TexturedVertex.class);
        buffer.setIndexData(indices);
    }

    private int numberOfTilesWide()
    {
        int width = configuration.getGraphics().getWidth();
        int extra = width % TILE_SIZE == 0 ? 0 : 1;
        int tiles = width / TILE_SIZE + extra;

        // We add one because we want an additional tile for overflow
        return tiles + 1;
    }

    private int numberOfTilesHigh()
    {
        int height = configuration.getGraphics().getHeight();
        int extra = height % TILE_SIZE == 0 ? 0 : 1;
        int tiles = height / TILE_SIZE + extra;

        // We add one because we want an additional tile for overflow
        return tiles + 1;
    }

    private int pad(int size)
    {
        int output = size;
        int diff = output % TILE_SIZE;
        if (diff != 0)
        {
            output += TILE_SIZE - diff;
        }
        return output;
    }

    private float withinMapPartX(float absoluteX)
    {
        float relativeX = absoluteX;
        while (relativeX < 0.0F)
        {
            relativeX += Constants.HORIZONTAL_METERS_PER_MAP_PART;
        }
        while (relativeX > Constants.HORIZONTAL_METERS_PER_MAP_PART)
        {
            relativeX -= Constants.HORIZONTAL_METERS_PER_MAP_PART;
        }
        return relativeX;
    }

    private float withinMapPartY(float absoluteY)
    {
        float relativeY = absoluteY;
        while (relativeY < 0.0F)
        {
            relativeY += Constants.VERTICAL_METERS_PER_MAP_PART;
        }
        while (relativeY > Constants.VERTICAL_METERS_PER_MAP_PART)
        {
            relativeY -= Constants.VERTICAL_METERS_PER_MAP_PART;
        }
        return relativeY;
    }

    private float locationWithinTile(float coordinate)
    {
        float relative = coordinate;
        while (relative < 0.0F)
        {
            relative += Constants.METERS_PER_TILE;
        }
        while (relative > Constants.METERS_PER_TILE)
        {
            relative -= Constants.METERS_PER_TILE;
        }
        return relative;
    }

    /**
     * Renders the visible parts of the map.
     */
    public void render()
    {
        int numberOfTilesWide = numberOfTilesWide();
        int numberOfTilesHigh = numberOfTilesHigh();

        for (int y = 0; y < numberOfTilesHigh; y++)
        {
            for (int x = 0; x < numberOfTilesWide; x++)
            {
                int index = (x + y * numberOfTilesWide) * NUMBER_OF_VERTICES_PER_TILE;

                float tileX = player.getX() - Utility.toMeters(pad(configuration.getGraphics().getWidth() / 2)) + x * METERS_PER_TILE;
                float tileY = player.getY() - Utility.toMeters(pad(configuration.getGraphics().getHeight() / 2)) + y * METERS_PER_TILE;

                int tilePartX = (int) (tileX / HORIZONTAL_TILES_PER_PART);
                int tilePartY = (int) (tileY / VERTICAL_TILES_PER_PART);

                if (tileX < 0)
                {
                    tilePartX--;
                }
                if (tileY < 0)
                {
                    tilePartY--;
                }

                MapPart part = loadedPartsByIdentifier.get(MapPartIdentifier.of(tilePartX, tilePartY));

                float withinMapPartX = withinMapPartX(tileX);
                float withinMapPartY = withinMapPartY(tileY);

                int tileWithinPartX = (int) (withinMapPartX / METERS_PER_TILE);
                int tileWithinPartY = (int) (withinMapPartY / METERS_PER_TILE);

                MapTile tile = part.getTile(tileWithinPartX, tileWithinPartY);

                float playerOffsetInMetersX = locationWithinTile(player.getX());
                float playerOffsetInMetersY = locationWithinTile(player.getY());
                int playerOffsetInPixelsX = Utility.toPixels(playerOffsetInMetersX);
                int playerOffsetInPixelsY = Utility.toPixels(playerOffsetInMetersY);

                int left = TILE_SIZE * x - playerOffsetInPixelsX;
                int right = TILE_SIZE * x + TILE_SIZE - playerOffsetInPixelsX;
                int bottom = TILE_SIZE * y - playerOffsetInPixelsY;
                int top = TILE_SIZE * y + TILE_SIZE - playerOffsetInPixelsY;

                float textureLeft = tile.getTextureCoordinateLeft();
                float textureBottom = tile.getTextureCoordinateBottom();
                float textureRight = tile.getTextureCoordinateRight();
                float textureTop = tile.getTextureCoordinateTop();

                vertices[index + 0].setValues(left, top, textureLeft, textureTop);
                vertices[index + 1].setValues(left, bottom, textureLeft, textureBottom);
                vertices[index + 2].setValues(right, bottom, textureRight, textureBottom);
                vertices[index + 3].setValues(right, top, textureRight, textureTop);
            }
        }

        buffer.setVertexData(vertices);

        shader.use();
        shader.setMatrix("param_projection", Matrix4.ortho(0.0F, 800.0F, 0.0F, 600.0F, 1.0F, -1.0F));
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        tileset001.bind();
        buffer.draw();
        shader.end();
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
        assetManager.unsubscribe(shader);
        assetManager.unsubscribe(tileset001);
        buffer.dispose();
    }
}
