package com.antonjohansson.game.asset;

import static com.antonjohansson.game.asset.map.MapConstants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.asset.map.MapConstants.VERTICAL_TILES_PER_PART;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.antonjohansson.game.asset.common.IAsset;
import com.antonjohansson.game.asset.map.MapPart;
import com.antonjohansson.game.asset.map.MapPartLoader;
import com.antonjohansson.game.asset.map.raw.MapData;
import com.antonjohansson.game.asset.map.raw.MapDataTile;

/**
 * Default implementation of {@link IAssetManager}.
 */
public class AssetManager implements IAssetManager
{
    private final Map<Class<? extends IAsset>, IAssetLoader<? extends IAsset, ?>> loaders = new HashMap<>();

    public AssetManager()
    {
        loaders.put(MapPart.class, new MapPartLoader());
        loaders.put(Texture.class, new TextureLoader());
    }

    @Override
    public void initialize()
    {
        String assetLocation = System.getProperty("assetLocation");
        if (!isValid(assetLocation))
        {
            throw new RuntimeException("The given asset location is invalid");
        }
        loaders.values().forEach(loader -> loader.setAssetLocation(assetLocation));

//        dummy(assetLocation);
    }

    @SuppressWarnings("unused")
    private void dummy(String assetLocation)
    {
        MapDataTile[][] tiles = new MapDataTile[HORIZONTAL_TILES_PER_PART][VERTICAL_TILES_PER_PART];
        for (int x = 0; x < HORIZONTAL_TILES_PER_PART; x++)
        {
            for (int y = 0; y < VERTICAL_TILES_PER_PART; y++)
            {
                MapDataTile tile = new MapDataTile();
                tile.setTilesetId(x);
                tile.setTileId(y);
                tiles[x][y] = tile;
            }
        }

        MapData data = new MapData();
        data.setTiles(tiles);

        String json = MapPartLoader.MAPPER.toJson(data);
        System.out.println(json);

        try (Writer writer = new FileWriter(assetLocation + "maps/+00050.-00050.map"))
        {
            writer.write(json);
            writer.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean isValid(String assetLocation)
    {
        if (assetLocation == null)
        {
            return false;
        }
        File directory = new File(assetLocation);
        return directory.exists()
            && directory.isDirectory();
    }

    @Override
    public <A extends IAsset> A getAsset(Class<A> type, Object identifier)
    {
        IAssetLoader<A, Object> loader = loader(type);
        if (identifier.getClass() != loader.getIdentifierType())
        {
            throw new RuntimeException("Cannot load assets of type '" + type.getSimpleName() + "' cannot be loaded using a '" + identifier.getClass().getSimpleName() + "'");
        }
        return loader.load(identifier);
    }

    @SuppressWarnings("unchecked")
    private <A extends IAsset> IAssetLoader<A, Object> loader(Class<A> type)
    {
        IAssetLoader<A, Object> loader = (IAssetLoader<A, Object>) loaders.get(type);
        return loader;
    }
}
