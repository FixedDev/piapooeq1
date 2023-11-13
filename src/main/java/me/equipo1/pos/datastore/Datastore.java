package me.equipo1.pos.datastore;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Datastore {
    CompletableFuture<Map<String, Integer>> loadInventory();

    CompletableFuture<Void> saveInventory(Inventory inventory);

    CompletableFuture<Collection<ItemData>> loadAllItemData();

    CompletableFuture<Void> deleteItem(ItemData itemData);

    CompletableFuture<Void> updateItem(ItemData itemData);

    CompletableFuture<Void> saveItem(ItemData itemData);

    CompletableFuture<ItemData> findItem(String id);
}
