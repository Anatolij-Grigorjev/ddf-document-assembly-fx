package lt.tiem625.docbuild.components;

import javafx.scene.Parent;

import java.util.concurrent.ConcurrentHashMap;

public class ViewsRepository {

    private ViewsRepository() {
        throw new UnsupportedOperationException("static helper");
    }

    private static final ConcurrentHashMap<ViewsKeys, Parent> loadedViews = new ConcurrentHashMap<>();

    public static Parent store(ViewsKeys key, Parent view) {
        return loadedViews.put(key, view);
    }

    public static Parent getAt(ViewsKeys key) {
        return loadedViews.get(key);
    }
}
