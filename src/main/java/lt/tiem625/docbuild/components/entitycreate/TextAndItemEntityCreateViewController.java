package lt.tiem625.docbuild.components.entitycreate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lt.tiem625.docbuild.ViewableEntity;
import lt.tiem625.docbuild.components.ValueDialogViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for views created using the FXML view <code>fxml/text-and-dependant-entity-builder.fxml</code>
 * </p>
 *
 * @param <E> type of entity being created
 * @param <I> type of dependant item picked for creation
 */
public class TextAndItemEntityCreateViewController<E extends ViewableEntity, I extends ViewableEntity> implements Initializable, ValueDialogViewController<E> {

    @FXML
    private Label lblTextInputName;

    @FXML
    private Label lblDependantInputName;

    @FXML
    private Label lblPickedDependant;

    @FXML
    private Button btnCreateEntity;

    @FXML
    private TextField tfTextInput;

    private final ObjectProperty<E> currentValue = new SimpleObjectProperty<>();
    private final ObjectProperty<I> pickedDependantItem = new SimpleObjectProperty<>();
    private final StringProperty presentText = new SimpleStringProperty();

    private Stage dialogWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCreateEntity.disableProperty()
                .bind(Bindings.or(
                        Bindings.isNull(pickedDependantItem),
                        Bindings.isEmpty(presentText))
                );
    }

    @FXML
    private void onDiscardClicked() {
        currentValue.set(null);
        dialogWindow.close();
    }

    @FXML
    private void onCreateClicked() {

    }

    @FXML
    private void onPickDependantClicked() {

    }

    @Override
    public ObservableValue<E> beginValueDialogContext(Stage dialogContext) {
        dialogWindow = dialogContext;
        return currentValue;
    }
}
