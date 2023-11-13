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

    void deleteItem(ItemData itemData);

    /**
     * Adds the specified quantity of items into the inventory.
     *
     * @param itemData The item to add more inventory of.
     * @param quantity The quantity of the item.
     */
    void addItem(ItemData itemData, int quantity);

    /**
     * Sells an item with a specified quantity
     *
     * @param itemData The item to sell
     * @param quantity The quantity of items to sell.
     * @return if the item could be sold.
     */
    default boolean sell(ItemData itemData, int quantity) {
        return sell(itemData,quantity,false);
    }

    /**
     * Sells an item with a specified quantity
     *
     * @param itemData The item to sell
     * @param quantity The quantity of items to sell.
     * @param force    if the item should be sold(updated) anyways.
     * @return if the item could be sold.
     */
    boolean sell(ItemData itemData, int quantity, boolean force);


    /**
     * If the inventory contains enough of the specified item data.
     *
     * @param itemData The item to check stock for.
     * @param quantity The quantity of the item that should be in stock.
     * @return If there's enough stock of the item.
     */
    boolean has(ItemData itemData, int quantity);

    int stock(ItemData itemData);

    /**
     * Returns an item into the inventory if it was returned by a client.
     *
     * @param itemData The item to return
     * @param quantity The quantity of the returned item.
     * @return if the item could be returned.
     */
    boolean returnItem(ItemData itemData, int quantity);


}
