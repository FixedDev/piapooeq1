package me.equipo1.pos.items;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ItemRegistry {
    CompletableFuture<ItemData> getItem(String id);

    Optional<ItemData> getIfCached(String id);

    CompletableFuture<Void> updateItem(ItemData itemData);

    CompletableFuture<Void> saveItem(ItemData itemData);

    CompletableFuture<Void> saveItem(String itemId);

    CompletableFuture<Void> deleteItem(ItemData itemData);

    CompletableFuture<Collection<ItemData>> allItems();

    CompletableFuture<Void> load();

}
