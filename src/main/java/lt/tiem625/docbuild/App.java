package lt.tiem625.docbuild;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lt.tiem625.docbuild.components.selectableitemdialog.SelectableItemDialogController;
import lt.tiem625.docbuild.components.selectableitemdialog.ValueBuilder;
import lt.tiem625.docbuild.data.StructureType;

import java.io.IOException;
import java.util.Set;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary").view, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {

        ViewWithController<SelectableItemDialogController<StructureType>> loadedFXML = loadFXML(fxml);

        loadedFXML.controller.setDialogData(
                null,
                Set.of(
                        new StructureType("SQL Table"),
                        new StructureType("SQL View"),
                        new StructureType("CSV File"),
                        new StructureType("XLSX File")
                ), ValueBuilder.notSupported()
        );
        StructureType selectedStructureType = SelectableItemDialogController.setupAndRunDialogScene(loadedFXML.view);
        System.out.println("Selected structure type: " + (selectedStructureType != null ? selectedStructureType.asView() : "NULL"));
    }


    private static <T> ViewWithController<T> loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/" + fxml + ".fxml"));
        return new ViewWithController<T>(fxmlLoader.load(), fxmlLoader.getController());
    }

    record ViewWithController<T>(Parent view, T controller) {
    }

    public static void main(String[] args) {
        launch();
    }

}