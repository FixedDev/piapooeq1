package me.equipo1.pos.datastore;

import me.equipo1.pos.database.SQLiteDbConnection;
import me.equipo1.pos.database.SQLiteStatements;
import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.inventory.PojoInventory;
import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.items.PojoItemData;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SQLiteDatastore implements Datastore {

    private final SQLiteDbConnection dbConnection;

    public SQLiteDatastore(SQLiteDbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public CompletableFuture<Map<String, Integer>> loadInventory() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, Integer> inventoryStock = new HashMap<>();

            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.LOAD_INV_QUERY);
                 ResultSet set = statement.executeQuery()) {

                while (set.next()) {
                    String id = set.getString(1);
                    int stock = set.getInt(2);

                    inventoryStock.put(id, stock);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return inventoryStock;
        });
    }

    @Override
    public CompletableFuture<Void> saveInventory(Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> {
            if (!(inventory instanceof PojoInventory inv)) {
                throw new IllegalArgumentException("Can't save not PojoInventory inventories");
            }

            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.SAVE_INV)) {

                for (Map.Entry<String, Integer> entry : inv.stock().entrySet()) {
                    String id = entry.getKey();
                    Integer stock = entry.getValue();

                    if (inv.changedStocks().remove(id)) {
                        statement.setString(1, id);
                        statement.setInt(2, stock);
                    }
                    statement.addBatch();
                }

                statement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public CompletableFuture<Collection<ItemData>> loadAllItemData() {
        return CompletableFuture.supplyAsync(() -> {
            List<ItemData> itemsData = new ArrayList<>();

            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.LOAD_ALL_ITEMS);
                 ResultSet set = statement.executeQuery()) {

                while (set.next()) {
                    ItemData data = loadData(set);

                    itemsData.add(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return itemsData;
        });
    }

    @Override
    public CompletableFuture<ItemData> findItem(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.LOAD_ITEM)) {

                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        return loadData(set);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    private ItemData loadData(ResultSet set) throws SQLException {
        String id = set.getString("item_id");
        String name = set.getString("name");
        double price = set.getDouble("price");
        int minStock = set.getInt("min_stock");

        return new PojoItemData(id, name, price, minStock);
    }

    @Override
    public CompletableFuture<Void> deleteItem(ItemData itemData) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.DELETE_ITEM)) {

                statement.setString(1, itemData.id());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public CompletableFuture<Void> updateItem(ItemData itemData) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.UPDATE_ITEM)) {

                statement.setString(1, itemData.name());
                statement.setInt(2, itemData.minQuantity());
                statement.setDouble(3, itemData.price());
                statement.setString(4, itemData.id());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public CompletableFuture<Void> saveItem(ItemData itemData) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = dbConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQLiteStatements.SAVE_ITEM)) {

                statement.setString(1, itemData.id());
                statement.setString(2, itemData.name());
                statement.setInt(3, itemData.minQuantity());
                statement.setDouble(4, itemData.price());

                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

}
