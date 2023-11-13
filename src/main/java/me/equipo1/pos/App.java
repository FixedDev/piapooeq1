package me.equipo1.pos;

import me.equipo1.pos.database.SQLiteDbConnection;
import me.equipo1.pos.datastore.Datastore;
import me.equipo1.pos.datastore.SQLiteDatastore;
import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.inventory.InventoryLoader;
import me.equipo1.pos.items.DatastoreItemRegistry;
import me.equipo1.pos.items.ItemRegistry;
import me.equipo1.pos.sales.CartManager;
import me.equipo1.pos.views.*;
import me.equipo1.pos.views.SalesView;
import me.equipo1.pos.views.inventory.CreateItemView;
import me.equipo1.pos.views.inventory.DeleteView;
import me.equipo1.pos.views.inventory.RegisterView;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.equipo1.pos.Bootstrap.ABARROTES_BISONTE;

public class App {

    private Logger logger;
    private Scanner scanner;
    private SQLiteDbConnection dbConnection;

    private View currentView;
    private Inventory inventory;
    private ItemRegistry registry;
    private Datastore datastore;

    public void init() {
        logger = Logger.getLogger("AbBisonte");

        initDb();
        initHandlers();

        scanner = new Scanner(System.in);

        ViewManager viewManager = new ViewManager();
        CartManager cartManager = new CartManager(inventory);

        viewManager.registerView("main", new MainView(scanner, viewManager));
        viewManager.registerView("inventory", new InventoryView(viewManager, scanner));
        viewManager.registerView("sales", new SalesView(viewManager, scanner, cartManager));
        viewManager.registerView("register", new RegisterView(scanner, viewManager, inventory));
        viewManager.registerView("create-item", new CreateItemView(scanner, viewManager, inventory));
        viewManager.registerView("delete", new DeleteView(scanner, viewManager, inventory));

        currentView = viewManager.getView("main").get();

        while (currentView != null) {
            try {
                currentView = currentView.show();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Un error ocurrio en la ejecucion de una vista", e);
                currentView = viewManager.getView("main").get();
            }
        }

        View.clear();
        View.println(ABARROTES_BISONTE);

        System.out.println("Gracias por usar Abarrotes Bisonte.");
        System.out.println("Adios:D");
    }

    private void initDb() {
        logger.log(Level.INFO, "Inicializando base de datos...");

        dbConnection = new SQLiteDbConnection(logger);

        Map<String, Object> properties = new HashMap<>();
        properties.put("file-dir", System.getProperty("db-file", "database.db"));

        File file = new File("database.db");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.log(Level.WARNING, "No pudo ser creado el archivo de la base de datos, podrian perderse datos!", e);
            }
        }

        try {
            dbConnection.connect(properties);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "No pudimos conectarnos correctamente a la base de datos, la ejecucion no puede continuar.", e);

            System.exit(1);
            return;
        }

        logger.log(Level.INFO, "Base de datos inicializada correctamente.");
    }

    private void initHandlers() {
        datastore = new SQLiteDatastore(dbConnection);
        registry = new DatastoreItemRegistry(datastore);

        logger.log(Level.INFO, "Cargando inventario desde la base de datos...");
        InventoryLoader loader = new InventoryLoader(registry, datastore);
        inventory = loader.loadInventory().join();
        logger.log(Level.INFO, "Inventario cargado.");

    }
}
