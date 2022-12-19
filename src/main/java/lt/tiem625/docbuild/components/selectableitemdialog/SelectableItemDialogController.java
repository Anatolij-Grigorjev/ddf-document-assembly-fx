package lt.tiem625.docbuild.components.selectableitemdialog;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lt.tiem625.docbuild.ViewableEntity;

import java.net.URL;
import java.util.*;

public class SelectableItemDialogController<T extends ViewableEntity> implements Initializable {

    @FXML
    private Label changingEntityLabel;

    @FXML
    private TextField searchItemTextField;

    @FXML
    private ListView<T> suggestionsListView;

    private List<T> suggestions;
    private final StringProperty changingValueProperty = new SimpleStringProperty("");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bindTextFieldToPrevValue();
        bindListOptionsToCurrentFieldText();

        setDialogData(null, null);
    }

    private void bindListOptionsToCurrentFieldText() {
        suggestionsListView.itemsProperty().bind(
                new SimpleObjectProperty<>(suggestions)
                        .map(list -> {
                            var filteredSuggestions = list.stream()
                                    .filter(suggestion -> suggestion.asView().contains(searchItemTextField.getText()))
                                    .toList();
                            return FXCollections.observableList(filteredSuggestions);
                        })
        );
    }

    private void bindTextFieldToPrevValue() {
        searchItemTextField.textProperty().bindBidirectional(changingValueProperty);
    }

    private void bindLabelToPrevValue(String view) {
        changingEntityLabel.setText(String.format("Changing from previous value '%s'...", view));
    }

    public void setDialogData(T prevValue, Set<? extends T> suggestions) {
        Optional.ofNullable(prevValue).map(ViewableEntity::asView).ifPresent(view -> {
            bindLabelToPrevValue(view);
            changingValueProperty.setValue(view);
        });
        this.suggestions = suggestions != null ? new ArrayList<>(suggestions) : List.of();

    }
}
