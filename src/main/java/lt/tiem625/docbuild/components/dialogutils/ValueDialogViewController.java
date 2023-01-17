package lt.tiem625.docbuild.components.dialogutils;

import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public interface ValueDialogViewController<T> {

    ObservableValue<T> beginValueDialogContext(Stage dialogContext);
}
