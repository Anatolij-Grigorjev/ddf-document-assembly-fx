package lt.tiem625.docbuild.components.dialogutils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Objects;
import java.util.Optional;

public class Alerts {

    private Alerts() {
        throw new UnsupportedOperationException("static helper");
    }

    public static void alertErrorOK(String alertText) {
        Objects.requireNonNull(alertText);
        var alert = new Alert(Alert.AlertType.ERROR, alertText, ButtonType.OK);
        alert.showAndWait();
    }

    public static AlertAnswer alertPromptOKCancel(String promptText) {
        Objects.requireNonNull(promptText);
        var alert = new Alert(Alert.AlertType.CONFIRMATION, promptText, ButtonType.CANCEL, ButtonType.OK);
        Optional<ButtonType> pressedButton = alert.showAndWait();

        return AlertAnswer.forButton(pressedButton.orElse(null))
                .orElseThrow(() -> new IllegalStateException("No alert answer to parse from dialog button " + pressedButton.orElse(null)));
    }
}
