package me.equipo1.pos.items;

import me.equipo1.pos.datastore.Datastore;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DatastoreItemRegistry implements ItemRegistry {

    private final Datastore datastore;
    private final Map<String, ItemData> itemDataCache;

    public DatastoreItemRegistry(Datastore datastore) {
        this.datastore = datastore;
        itemDataCache = new HashMap<>();
    }

    @Override
    public CompletableFuture<ItemData> getItem(String id) {
        ItemData itemData = itemDataCache.get(id);

        if (itemData != null) {
            return CompletableFuture.completedFuture(itemData);
        }

        return datastore.findItem(id).thenApply(data -> {
            itemDataCache.put(id, data);

            return data;
        });
    }

    @Override
    public Optional<ItemData> getIfCached(String id) {
        return Optional.ofNullable(itemDataCache.get(id));
    }

    @Override
    public CompletableFuture<Void> saveItem(ItemData itemData) {
        itemDataCache.put(itemData.id(), itemData);

        return datastore.saveItem(itemData);
    }

    @Override
    public CompletableFuture<Void> saveItem(String itemId) {
        return getIfCached(itemId).map(this::saveItem).orElse(CompletableFuture.completedFuture(null));
    }

    @Override
    public CompletableFuture<Void> deleteItem(ItemData itemData) {
        itemDataCache.remove(itemData.id());

        return datastore.deleteItem(itemData);
    }

    @Override
    public CompletableFuture<Void> load() {
        return datastore.loadAllItemData()
                .thenAccept(itemData -> {
                    for (ItemData itemDatum : itemData) {
                        itemDataCache.put(itemDatum.id(), itemDatum);
                    }
                });
    }
}
