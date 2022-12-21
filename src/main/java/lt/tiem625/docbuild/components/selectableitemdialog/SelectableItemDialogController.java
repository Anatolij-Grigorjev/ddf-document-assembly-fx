package lt.tiem625.docbuild.components.selectableitemdialog;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import lt.tiem625.docbuild.ViewableEntity;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;

public class SelectableItemDialogController<T extends ViewableEntity> implements Initializable {

    @FXML
    private Label changingEntityLabel;

    @FXML
    private TextField searchItemTextField;

    @FXML
    private ListView<T> suggestionsListView;

    private T prevValue;

    private ObservableList<T> suggestionsObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        suggestionsListView.setCellFactory(buildEntitySuggestionsCellFactory());
        suggestionsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, prevVal, nextVal) ->
                        Optional.ofNullable(observable.getValue())
                                .map(ViewableEntity::asView)
                                .ifPresent(view -> Platform.runLater(() -> searchItemTextField.setText(view)))
                );
        bindSuggestionsListToCurrentFieldText();
        setDialogData(null, null);
    }

    protected Callback<ListView<T>, ListCell<T>> buildEntitySuggestionsCellFactory() {
        return listView -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.asView());
                }
            }
        };
    }

    private void bindSuggestionsListToCurrentFieldText() {

        suggestionsObservableList = FXCollections.observableList(new ArrayList<>());
        FilteredList<T> filteredSuggestionsList = new FilteredList<>(suggestionsObservableList);
        suggestionsListView.itemsProperty().setValue(filteredSuggestionsList);
        filteredSuggestionsList.predicateProperty().bind(
                searchItemTextField.textProperty()
                        .flatMap(text -> new SimpleObjectProperty<>(suggestion -> StringUtils.containsIgnoreCase(suggestion.asView(), text)))
        );
    }

    private void setTextFieldFromPrevValue() {
        if (prevValue == null) {
            searchItemTextField.setText("");
        } else {
            searchItemTextField.setText(prevValue.asView());
        }
    }

    private void setLabelTextFromPrevValue() {
        Optional.ofNullable(prevValue).ifPresentOrElse(
                value -> {
                    changingEntityLabel.setText(String.format("Changing from previous value '%s'...", value.asView()));
                },
                () -> changingEntityLabel.setText("Setting entity value...")
        );

    }

    public void setDialogData(T prevValue, Set<? extends T> suggestions) {
        this.prevValue = prevValue;
        setLabelTextFromPrevValue();
        setTextFieldFromPrevValue();

        List<T> suggestionsList = suggestions != null ? new ArrayList<>(suggestions) : List.of();
        suggestionsObservableList.setAll(suggestionsList);
    }
}
