package lt.tiem625.docbuild.components;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogBuilder {

    private Parent sceneContents;
    private String title;

    private DialogBuilder() {

    }

    public static DialogBuilder newDialog() {
        return new DialogBuilder();
    }

    public DialogBuilder withScene(Parent scene) {
        this.sceneContents = scene;
        return this;
    }

    public DialogBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public Stage build() {
        var dialog = new Stage(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        dialog.setScene(new Scene(sceneContents));

        return dialog;
    }
}
