package lt.tiem625.docbuild.components;

import java.util.concurrent.ConcurrentHashMap;

public class ViewsRepository {

    private ViewsRepository() {
        throw new UnsupportedOperationException("static helper");
    }

    private static final ConcurrentHashMap<ViewsKeys, ViewWithController<?>> loadedViews = new ConcurrentHashMap<>();

    public static ViewWithController<?> store(ViewsKeys key, ViewWithController<?> viewAndController) {
        loadedViews.put(key, viewAndController);
        return viewAndController;
    }

    public static <T> ViewWithController<T> getAt(ViewsKeys key) {
        return (ViewWithController<T>) loadedViews.get(key);
    }
}
