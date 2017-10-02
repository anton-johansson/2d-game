package com.antonjohansson.game.asset.map;

import static com.antonjohansson.game.asset.map.MapConstants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.asset.map.MapConstants.VERTICAL_TILES_PER_PART;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.DecimalFormat;

import com.antonjohansson.game.asset.IAssetLoader;
import com.antonjohansson.game.asset.map.raw.MapData;
import com.antonjohansson.game.asset.map.raw.MapDataTile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Loads {@link MapPart map parts}.
 */
public class MapPartLoader implements IAssetLoader<MapPart, MapPartIdentifier>
{
    public static final Gson MAPPER = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final int MINIMUM_INTEGER_DIGITS = 5;
    private static final DecimalFormat FORMAT = format();

    private static DecimalFormat format()
    {
        DecimalFormat format = new DecimalFormat("+#;-#");
        format.setMinimumIntegerDigits(MINIMUM_INTEGER_DIGITS);
        return format;
    }

    private String mapsLocation;

    @Override
    public void setAssetLocation(String assetLocation)
    {
        this.mapsLocation = assetLocation + "/maps/";
    }

    @Override
    public Class<MapPartIdentifier> getIdentifierType()
    {
        return MapPartIdentifier.class;
    }

    @Override
    public MapPart load(MapPartIdentifier identifier)
    {
        String fileName = FORMAT.format(identifier.getX()) + "." + FORMAT.format(identifier.getY()) + ".map";

        File file = new File(mapsLocation + fileName);
        try (Reader reader = new FileReader(file))
        {
            MapData data = MAPPER.fromJson(reader, MapData.class);

            MapTile[][] tiles = new MapTile[HORIZONTAL_TILES_PER_PART][VERTICAL_TILES_PER_PART];
            for (int y = 0; y < VERTICAL_TILES_PER_PART; y++)
            {
                for (int x = 0; x < HORIZONTAL_TILES_PER_PART; x++)
                {
                    MapDataTile tileData = data.getTiles()[x][y];
                    int tilesetId = tileData.getTilesetId();
                    int tileId = tileData.getTileId();
                    MapTile tile = new MapTile(tilesetId, tileId);
                    tiles[x][y] = tile;
                }
            }
            return new MapPart(tiles);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
