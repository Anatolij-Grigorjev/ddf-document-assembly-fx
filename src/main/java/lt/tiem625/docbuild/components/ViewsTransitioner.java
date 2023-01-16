package lt.tiem625.docbuild.components;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class ViewsTransitioner {

    public static final int VIEWPORT_WIDTH = 800;
    public static final int VIEWPORT_HEIGHT = 600;

    private final Stage stage;

    public ViewsTransitioner(Stage stage) {
        Objects.requireNonNull(stage);
        this.stage = stage;
    }


    public <T> void transitionToViewAtKey(ViewsKeys viewKey, Consumer<T> controllerDataSetter) {

        ViewWithController<T> viewWithController = ViewsRepository.getAt(viewKey);
        controllerDataSetter.accept(viewWithController.controller());
        var view = viewWithController.view();
        var scene =
                Optional.ofNullable(view.getScene())
                .orElse(new Scene(view, VIEWPORT_WIDTH, VIEWPORT_HEIGHT));
        stage.setScene(scene);
    }
}
