package lt.tiem625.docbuild.components.entitycreate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lt.tiem625.docbuild.ViewableEntity;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsRepository;
import lt.tiem625.docbuild.components.dialogutils.AbstractPropertyValueDialog;
import lt.tiem625.docbuild.components.dialogutils.DialogRunner;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;
import lt.tiem625.docbuild.components.selectableitempicker.ValueBuildBehavior;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Controller for views created using the FXML view <code>fxml/text-and-dependant-entity-builder.fxml</code>
 * </p>
 *
 * @param <E> type of entity being created
 * @param <I> type of dependant item picked for creation
 */
public class TextAndItemEntityCreateViewController<E extends ViewableEntity, I extends ViewableEntity> extends AbstractPropertyValueDialog<E> implements Initializable {

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

    private final ObjectProperty<I> pickedDependantItem = new SimpleObjectProperty<>();
    private final StringProperty presentText = new SimpleStringProperty();

    private Set<I> dependantsSet;
    private EntityConstructor<E, I> valueConstructor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCreateEntity.disableProperty()
                .bind(Bindings.or(
                        Bindings.isNull(pickedDependantItem),
                        Bindings.isEmpty(presentText))
                );
        tfTextInput.textProperty().bindBidirectional(presentText);
    }

    @FXML
    private void onDiscardClicked() {
        setCurrentFinal(null);
    }

    @FXML
    private void onCreateClicked() {
        E constructedValue = valueConstructor.constructFrom(tfTextInput.getText(), pickedDependantItem.getValue());
        setCurrentFinal(constructedValue);
    }

    @FXML
    private void onPickDependantClicked() {
        ViewWithController<SelectableItemPickerController<I>> dialogParts =
                ViewsRepository.getAt(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        dialogParts.controller().setDialogData(pickedDependantItem.getValue(), dependantsSet, ValueBuildBehavior.buildingNotSupported());
        Optional<I> pickedValue =
                DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY, "Select dependant...");
        pickedValue.ifPresent(value -> {
            pickedDependantItem.set(value);
            lblPickedDependant.setText(value.asView());
        });
    }

    public void setCreationContext(
            String textFieldLabel, String dependantPickLabel,
            String initialText, Set<I> pickableValues,
            EntityConstructor<E, I> entityConstructor) {

        Objects.requireNonNull(entityConstructor);
        if (pickableValues == null || pickableValues.isEmpty()) {
            throw new IllegalArgumentException("dependants must be picked from non-empty presets list!");
        }
        this.lblTextInputName.setText(textFieldLabel);
        this.lblDependantInputName.setText(dependantPickLabel);
        this.presentText.set(initialText);
        this.dependantsSet = pickableValues;
        this.valueConstructor = entityConstructor;
    }
}
