package lt.tiem625.docbuild.components.selectableitemdialog;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import lt.tiem625.docbuild.ViewableEntity;
import lt.tiem625.docbuild.components.DialogBuilder;
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
    private ValueBuilder<T> valueBuilder;
    private ObservableList<T> suggestionsObservableList;


    private static Stage dialogWindow;
    private static ViewableEntity decidedValue;

    public static <T extends ViewableEntity> T setupAndRunDialogScene(Parent contents) {
        dialogWindow = DialogBuilder.newDialog()
                .withScene(contents)
                .withTitle("Select entity")
                .build();

        dialogWindow.showAndWait();
        return (T) decidedValue;
    }

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
        setDialogData(null, null, ValueBuilder.notSupported());
    }

    @FXML
    private void onSubmitClicked() {
        if (getVisibleSuggestionsCount() == 1) {
            decidedValue = suggestionsListView.getItems().get(0);
            dialogWindow.close();
            return;
        }

        if (getVisibleSuggestionsCount() > 1) {
            showMustPickOnlyOneValueAlert();
            return;
        }

        if (!valueBuilder.canBuildFromText()) {
            showUnsupportedFeatureAlert();
            return;
        }

        decidedValue = valueBuilder.buildFromText(searchItemTextField.getText());
        dialogWindow.close();
    }

    private void showUnsupportedFeatureAlert() {

        var alertDialog =
                new Alert(Alert.AlertType.ERROR,
                        "Inventing new examples not supported for this entity, please chose from suggestions list!",
                        ButtonType.OK);
        alertDialog.showAndWait();
    }

    private void showMustPickOnlyOneValueAlert() {
        var alertDialog =
                new Alert(Alert.AlertType.INFORMATION,
                        "Please pick only one matching suggestion (with mouse or by typing more of its unique text)",
                        ButtonType.OK);
        alertDialog.showAndWait();
    }

    private int getVisibleSuggestionsCount() {
        return suggestionsListView.getItems().size();
    }

    @FXML
    private void onDiscardClicked() {
        decidedValue = this.prevValue;
        dialogWindow.close();
    }

    public void setDialogData(T prevValue, Set<? extends T> suggestions, ValueBuilder<T> valueBuilder) {
        Objects.requireNonNull(valueBuilder);
        this.valueBuilder = valueBuilder;
        this.prevValue = prevValue;
        decidedValue = this.prevValue;
        setLabelTextFromPrevValue();
        setTextFieldFromPrevValue();

        List<T> suggestionsList = suggestions != null ? new ArrayList<>(suggestions) : List.of();
        suggestionsObservableList.setAll(suggestionsList);
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
}
