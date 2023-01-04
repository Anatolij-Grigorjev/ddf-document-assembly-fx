package lt.tiem625.docbuild;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsLoader;
import lt.tiem625.docbuild.components.dialogutils.DialogRunner;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;
import lt.tiem625.docbuild.components.selectableitempicker.ValueBuilder;
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
        scene = new Scene(ViewsLoader.loadAndStoreViewAtKey(ViewsKeys.EXAMPLE).view(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot() throws IOException {

        ViewWithController<SelectableItemPickerController<StructureType>> loadedFXML = ViewsLoader.loadAndStoreViewAtKey(ViewsKeys.DIALOG_SELECT_STRUCTURE_TYPE);
        loadedFXML.controller().setDialogData(
                null,
                Set.of(
                        new StructureType("SQL Table"),
                        new StructureType("SQL View"),
                        new StructureType("CSV File"),
                        new StructureType("XLSX File")
                ), ValueBuilder.notSupported()
        );
        StructureType selectedStructureType = DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_STRUCTURE_TYPE);
        System.out.println("Selected structure type: " + (selectedStructureType != null ? selectedStructureType.asView() : "NULL"));
    }

    public static void main(String[] args) {
        launch();
    }

}