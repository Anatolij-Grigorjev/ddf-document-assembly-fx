package lt.tiem625.docbuild.components.selectableitemdialog;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lt.tiem625.docbuild.ViewableEntity;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class SelectableItemDialogController<T extends ViewableEntity> implements Initializable {

    @FXML
    private Label changingEntityLabel;

    @FXML
    private TextField searchItemTextField;

    @FXML
    private ListView<T> suggestionsListView;

    private Set<? extends T> suggestions;
    private final Property<T> prevValueProperty = new SimpleObjectProperty<>(null);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bindLabelToPrevValue();
        bindTextFieldToPrevValue();
//        suggestionsListView.itemsProperty()

        setDialogData(null, null);
    }

    private void bindTextFieldToPrevValue() {
        searchItemTextField.textProperty().bind(prevValueProperty.map(ViewableEntity::asView));
    }

    private void bindLabelToPrevValue() {
        changingEntityLabel.textProperty().bind(
                prevValueProperty.map(passedVal -> String.format("Changing set value '%s'...", passedVal.asView()))
        );
    }

    public void setDialogData(T prevValue, Set<? extends T> suggestions) {
        this.prevValueProperty.setValue(prevValue);
        this.suggestions = suggestions != null ? new HashSet<>(suggestions) : Set.of();
    }
}
