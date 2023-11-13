package me.equipo1.pos.database;

public final class SQLiteStatements {

    public static final String CREATE_ITEMS_TABLE = """
                create table if not exists items(
                    item_id TEXT PRIMARY KEY,
                    name TEXT,
                    min_stock int,
                    price real
                );
            """;

    public static final String CREATE_INV_TABLE = """
                create table if not exists inventory (
                    item_id TEXT UNIQUE,
                    item_stock INT,
                    FOREIGN KEY(item_stock) REFERENCES items(id) ON UPDATE CASCADE
                );
            """;

    public static final String LOAD_INV_QUERY = """
            select * from inventory;
            """;

    public static final String SAVE_INV = """
            insert into inventory (
                item_id,
                item_stock
            ) values(?, ?) on conflict(item_id) do update set item_stock = excluded.item_stock;
            """;

    public static final String LOAD_ALL_ITEMS = """
            select * from items;
            """;

    public static final String LOAD_ITEM = """
            select * from items where item_id = ?;
            """;

    public static final String DELETE_ITEM = """
            delete from items where item_id = ?;
            """;

    public static final String SAVE_ITEM = """
            insert into items (
                item_id,
                name,
                min_stock,
                price
            ) values (?,?,?,?);
            """;

    public static final String UPDATE_ITEM = """
            update items set
                name = ?,
                min_stock = ?,
                price = ?
            where item_id = ?;
            """;
}
