package lt.tiem625.docbuild.components.dialogutils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class AbstractPropertyValueDialog<T> implements ValueDialogViewController<T> {

    protected final ObjectProperty<T> currentValue = new SimpleObjectProperty<>();

    protected Stage dialogWindow;

    /**
     * Set the typed required value into prop and close this dialog
     * @param newValue value to set, can be null
     */
    protected void setCurrentFinal(T newValue) {
        currentValue.set(newValue);
        dialogWindow.close();
    }

    @Override
    public ObservableValue<T> beginValueDialogContext(Stage dialogContext) {
        this.dialogWindow = dialogContext;
        return currentValue;
    }
}
