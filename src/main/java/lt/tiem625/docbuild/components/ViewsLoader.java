package lt.tiem625.docbuild.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ViewsLoader {

    private ViewsLoader() {
        throw new UnsupportedOperationException("static helper");
    }

    public static <T> ViewWithController<T> loadAndStoreViewAtKey(ViewsKeys viewKey) throws IOException {
        var loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource(viewKey.getFxmlPath()));
        Parent loadedView = loader.load();
        return (ViewWithController<T>) ViewsRepository.store(viewKey, new ViewWithController<>(loadedView, loader.getController()));
    }
}
