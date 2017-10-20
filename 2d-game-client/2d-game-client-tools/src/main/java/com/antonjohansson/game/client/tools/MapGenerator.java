package com.antonjohansson.game.client.tools;

import static com.antonjohansson.game.client.app.asset.map.MapPartLoader.FORMAT;
import static com.antonjohansson.game.client.app.common.Constants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.client.app.common.Constants.VERTICAL_TILES_PER_PART;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Random;

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
        for (int y = -3; y <= 3; y++)
        {
            for (int x = -3; x <= 3; x++)
            {
                generate(x, y);
            }
        }
    }

    private static void generate(int mapX, int mapY)
    {
        MapDataTile[][] tiles = new MapDataTile[HORIZONTAL_TILES_PER_PART][VERTICAL_TILES_PER_PART];
        for (int x = 0; x < HORIZONTAL_TILES_PER_PART; x++)
        {
            for (int y = 0; y < VERTICAL_TILES_PER_PART; y++)
            {
                MapDataTile tile = new MapDataTile();
                tile.setTilesetId(1);
                tile.setTileId(new Random().nextInt(50) + 1);
                tiles[x][y] = tile;
            }
        }

        MapData data = new MapData();
        data.setTiles(tiles);

        String json = MapPartLoader.MAPPER.toJson(data);
//        System.out.println(json);

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
