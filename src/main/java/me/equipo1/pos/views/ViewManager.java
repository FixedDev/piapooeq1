package me.equipo1.pos.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ViewManager {
    private final Map<String, View> viewsById;

    public ViewManager() {
        viewsById = new HashMap<>();
    }

    public Optional<View> getView(String id) {
        return Optional.ofNullable(viewsById.get(id));
    }

    public void registerView(String id, View view) {
        if (viewsById.containsKey(id)) {
            throw new IllegalArgumentException("A view with the id " + id + " already exists.");
        }

        viewsById.put(id, view);
    }

    public boolean viewExists(String id) {
        return viewsById.containsKey(id);
    }
}
