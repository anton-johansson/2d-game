package com.antonjohansson.game.asset;

import static com.antonjohansson.game.asset.map.MapConstants.HORIZONTAL_TILES_PER_PART;
import static com.antonjohansson.game.asset.map.MapConstants.VERTICAL_TILES_PER_PART;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.antonjohansson.game.asset.common.IAsset;
import com.antonjohansson.game.asset.map.MapPart;
import com.antonjohansson.game.asset.map.MapPartLoader;
import com.antonjohansson.game.asset.map.TileSet;
import com.antonjohansson.game.asset.map.TilesetLoader;
import com.antonjohansson.game.asset.map.raw.MapData;
import com.antonjohansson.game.asset.map.raw.MapDataTile;
import com.antonjohansson.game.asset.texture.Texture;
import com.antonjohansson.game.asset.texture.TextureLoader;

/**
 * Default implementation of {@link IAssetManager}.
 */
public class AssetManager implements IAssetManagerController
{
    private final Map<Class<? extends IAsset>, IAssetLoader<? extends IAsset, ?>> loaders = new HashMap<>();
    private final Map<Class<?>, Map<Object, AssetHolder<? extends IAsset>>> storages = new HashMap<>();

    public AssetManager()
    {
        loaders.put(MapPart.class, new MapPartLoader());
        loaders.put(Texture.class, new TextureLoader());
        loaders.put(TileSet.class, new TilesetLoader());
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

        //dummy(assetLocation);
    }

    @Override
    public void dispose()
    {
        for (Entry<Class<?>, Map<Object, AssetHolder<?>>> entry : storages.entrySet())
        {
            Map<Object, AssetHolder<?>> storage = entry.getValue();
            if (!storage.isEmpty())
            {
                Class<?> assetType = entry.getKey();
                System.err.println("There are " + storage.size() + " asset(s) of type '" + assetType.getSimpleName() + "' that are still subscribed to!");
            }
        }
        storages.clear();
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
                tile.setTilesetId(1);
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
    public <A extends IAsset> A subscribe(Class<A> type, Object identifier)
    {
        AssetHolder<A> holder = holder(type, identifier, true);
        if (holder != null)
        {
            holder.subscribe();
            return holder.getAsset();
        }
        IAssetLoader<A, Object> loader = loader(type);
        if (identifier.getClass() != loader.getIdentifierType())
        {
            throw new RuntimeException("Cannot load assets of type '" + type.getSimpleName() + "' cannot be loaded using a '" + identifier.getClass().getSimpleName() + "'");
        }
        A asset = loader.load(identifier, this);
        holder = new AssetHolder<>(asset);
        holder.subscribe();
        Map<Object, AssetHolder<? extends IAsset>> storage = storages.get(type);
        storage.put(identifier, holder);
        return asset;
    }

    @Override
    public <A extends IAsset> void unsubscribe(Class<A> type, Object identifier)
    {
        AssetHolder<A> holder = holder(type, identifier, false);
        if (holder == null)
        {
            throw new IllegalStateException("No asset was found for type '" + type.getSimpleName() + "' and identifier '" + identifier
                + "'. You are either unsubscribing from an asset that has never been subscribed to, or has already been unsubscribed from enough.");
        }
        holder.unsubscribe();
        if (!holder.isSubscribedTo())
        {
            IAssetLoader<A, Object> loader = loader(type);
            loader.dispose(holder.getAsset(), this);
            Map<?, ?> storage = storages.get(type);
            storage.remove(identifier);
        }
    }

    @SuppressWarnings("unchecked")
    private <A extends IAsset> IAssetLoader<A, Object> loader(Class<A> type)
    {
        IAssetLoader<A, Object> loader = (IAssetLoader<A, Object>) loaders.get(type);
        return loader;
    }

    @SuppressWarnings("unchecked")
    private <A extends IAsset> AssetHolder<A> holder(Class<A> type, Object identifier, boolean createStorageIfAbsent)
    {
        Map<Object, AssetHolder<? extends IAsset>> storage = createStorageIfAbsent
            ? storages.computeIfAbsent(type, t -> new HashMap<>())
            : storages.get(type);

        if (storage == null)
        {
            throw new IllegalStateException("No storage was found for type '" + type.getSimpleName() + ". Are you unsubscribing from an asset type that has never been loaded?");
        }
        return (AssetHolder<A>) storage.get(identifier);
    }
}
