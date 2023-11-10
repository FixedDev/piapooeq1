package me.equipo1.pos.inventory;

import me.equipo1.pos.items.ItemData;

import java.util.Optional;

public interface Inventory {

    Optional<ItemData> itemById(String id);

    /**
     * Registers a new item in the inventory, fails silently if the item is already
     * registered.
     *
     * @param itemData The item to register.
     */
    void registerItem(ItemData itemData);

    /**
     * Adds the specified quantity of items into the inventory.
     *
     * @param itemData     The item to add more inventory of.
     * @param quantity The quantity of the item.
     */
    void addItem(ItemData itemData, int quantity);

    /**
     * Sells an item with a specified quantity
     *
     * @param itemData     The item to sell
     * @param quantity The quantity of items to sell.
     * @return if the item could be sold.
     */
    boolean sell(ItemData itemData, int quantity);

    /**
     * Returns an item into the inventory if it was returned by a client.
     *
     * @param itemData     The item to return
     * @param quantity The quantity of the returned item.
     * @return if the item could be returned.
     */
    boolean returnItem(ItemData itemData, int quantity);

}
