package com.antonjohansson.game.client.tools;

import static com.antonjohansson.game.client.app.asset.map.MapPartLoader.FORMAT;
import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_TILES_PER_PART;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.antonjohansson.game.client.app.asset.map.MapPartLoader;
import com.antonjohansson.game.client.app.asset.map.raw.MapData;
import com.antonjohansson.game.client.app.asset.map.raw.MapDataTile;

/**
 * Generates a dummy map.
 */
public class MapGenerator
{
    /** Entry-point. */
    public static void main(String[] args)
    {
        final int mapX = 50;
        final int mapY = -50;

        MapDataTile[][] tiles = new MapDataTile[HORIZONTAL_TILES_PER_PART][VERTICAL_TILES_PER_PART];
        for (int x = 0; x < HORIZONTAL_TILES_PER_PART; x++)
        {
            for (int y = 0; y < VERTICAL_TILES_PER_PART; y++)
            {
                MapDataTile tile = new MapDataTile();
                tile.setTilesetId(1);
                tile.setTileId(y + 1);
                tiles[x][y] = tile;
            }
        }

        MapData data = new MapData();
        data.setTiles(tiles);

        String json = MapPartLoader.MAPPER.toJson(data);
        System.out.println(json);

        String fileName = FORMAT.format(mapX) + "." + FORMAT.format(mapY) + ".map";
        try (Writer writer = new FileWriter(getAssetLocation() + "maps/" + fileName))
        {
            writer.write(json);
            writer.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String getAssetLocation()
    {
        return new File("pom.xml").getAbsoluteFile().getParentFile().getParent() + "/2d-game-client-application/src/main/assets/";
    }
}
