package lt.tiem625.docbuild.components.selectableitemdialog;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private List<T> suggestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        suggestionsListView.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.asView());
                }
            }
        });

        suggestionsListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, prevVal, nextVal) ->
                        Optional.ofNullable(observable.getValue())
                                .map(ViewableEntity::asView)
                                .ifPresent(searchItemTextField::setText)
                );
        setDialogData(null, null);
    }

    private void bindCurrentSuggestionsListToCurrentFieldText() {

        List<T> presentSuggestionsList = suggestions == null ? List.of() : suggestions;
        var filteredList = new FilteredList<>(FXCollections.observableList(presentSuggestionsList));
        suggestionsListView.itemsProperty().setValue(filteredList);
        filteredList.predicateProperty().bind(
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

        this.suggestions = suggestions != null ? new ArrayList<>(suggestions) : List.of();
        bindCurrentSuggestionsListToCurrentFieldText();
    }
}
