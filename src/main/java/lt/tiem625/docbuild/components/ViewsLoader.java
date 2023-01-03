package lt.tiem625.docbuild.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ViewsLoader {

    private ViewsLoader() {
        throw new UnsupportedOperationException("static helper");
    }

    public static <T> ViewWithController<T> loadAndStoreViewAtKey(ViewsKeys viewKey) throws IOException {

        var loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(viewKey.getFxmlPath()));
        loader.setControllerFactory(clazz -> {
            try {
                return viewKey.getControllerClazz()
                        .getDeclaredConstructor(ViewsKeys.class)
                        .newInstance(viewKey);
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                try {
                    return viewKey.getControllerClazz()
                            .getDeclaredConstructor()
                            .newInstance();
                } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Parent loadedView = loader.load();
        ViewsRepository.store(viewKey, loadedView);
        return new ViewWithController<>(loadedView, loader.getController());
    }
}
