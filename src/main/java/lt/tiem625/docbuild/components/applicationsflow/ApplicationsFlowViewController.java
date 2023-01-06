package lt.tiem625.docbuild.components.applicationsflow;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsRepository;
import lt.tiem625.docbuild.components.dialogutils.Alerts;
import lt.tiem625.docbuild.components.dialogutils.DialogRunner;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;
import lt.tiem625.docbuild.components.selectableitempicker.ValueBuilder;
import lt.tiem625.docbuild.data.Application;
import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.DataAsset;
import lt.tiem625.docbuild.datasource.MetadataProvider;

import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ApplicationsFlowViewController implements Initializable {

    @FXML
    private Label lblSourceDescription;

    @FXML
    private Label lblTargetDescription;

    private final ObjectProperty<BusinessApplication> sourceApplicationProp = new SimpleObjectProperty<>();
    private final ObjectProperty<BusinessApplication> targetApplicationProp = new SimpleObjectProperty<>();
    private MetadataProvider metadataProvider;
    private Consumer<ApplicationsFlow> selectionDoneCallback;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblSourceDescription.textProperty()
                .bind(sourceApplicationProp.map(businessApplication -> "Source Application:\n" + businessApplication.asView()));
        lblTargetDescription.textProperty()
                .bind(targetApplicationProp.map(businessApplication -> "Target Application:\n" + businessApplication.asView()));
    }

    @FXML
    private void onPickSourceApplicationClicked() {

        ViewWithController<SelectableItemPickerController<Application>> applicationSelectDialog
                = ViewsRepository.getAt(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        applicationSelectDialog.controller().setDialogData(
                businessApplicationPropAsSubtype(sourceApplicationProp, Application.class),
                new HashSet<>(metadataProvider.getKnownApplications()),
                ValueBuilder.notSupported()
        );
        Application pickedApplication =
                DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY, "Select source application");
        sourceApplicationProp.set(pickedApplication);
    }

    @FXML
    private void onPickSourceAssetClicked() {

        ViewWithController<SelectableItemPickerController<DataAsset>> applicationSelectDialog
                = ViewsRepository.getAt(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        applicationSelectDialog.controller().setDialogData(
                businessApplicationPropAsSubtype(sourceApplicationProp, DataAsset.class),
                new HashSet<>(metadataProvider.getKnownDataAssets()),
                ValueBuilder.notSupported()
        );
        Application pickedApplication =
                DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY, "Select source data asset");
        sourceApplicationProp.set(pickedApplication);
    }

    @FXML
    private void onPickTargetApplicationClicked() {

    }

    @FXML
    private void onPickTargetAssetClicked() {

    }

    @FXML
    private void onSubmitSourceTargetClicked() {

        if (sourceApplicationProp.getValue() == null ||
                targetApplicationProp.getValue() == null) {
            Alerts.alertErrorOK("source and target must be set to continue");
            return;
        }
        selectionDoneCallback.accept(new ApplicationsFlow(sourceApplicationProp.getValue(), targetApplicationProp.getValue()));
    }

    public void setDataContext(
            BusinessApplication currentSource,
            BusinessApplication currentTarget,
            MetadataProvider metadataProvider,
            Consumer<ApplicationsFlow> selectionDoneCallback
    ) {

        Objects.requireNonNull(metadataProvider);
        Objects.requireNonNull(selectionDoneCallback);

        this.metadataProvider = metadataProvider;
        this.selectionDoneCallback = selectionDoneCallback;
        this.sourceApplicationProp.set(currentSource);
        this.targetApplicationProp.set(currentTarget);
    }


    private static <T extends BusinessApplication> T businessApplicationPropAsSubtype(ObjectProperty<BusinessApplication> prop,
                                                                                      Class<T> desiredSubTypeClazz) {

        BusinessApplication currentValue = prop.getValue();
        if (currentValue == null) {
            return null;
        }
        if (desiredSubTypeClazz.isAssignableFrom(currentValue.getClass())) {
            return (T) currentValue;
        }
        return null;
    }
}
