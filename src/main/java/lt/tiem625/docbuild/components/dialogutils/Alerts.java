package lt.tiem625.docbuild.components.dialogutils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class Alerts {

    private Alerts() {
        throw new UnsupportedOperationException("static helper");
    }

    public static void alertErrorOK(String alertText) {
        Objects.requireNonNull(alertText);
        var alert = new Alert(Alert.AlertType.ERROR, alertText, ButtonType.OK);
        alert.showAndWait();
    }
}
