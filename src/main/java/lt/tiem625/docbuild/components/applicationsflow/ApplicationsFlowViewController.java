package lt.tiem625.docbuild.components.applicationsflow;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsRepository;
import lt.tiem625.docbuild.components.dialogutils.Alerts;
import lt.tiem625.docbuild.components.dialogutils.DialogRunner;
import lt.tiem625.docbuild.components.entitycreate.TextAndItemEntityCreateViewController;
import lt.tiem625.docbuild.components.selectableitempicker.SelectableItemPickerController;
import lt.tiem625.docbuild.components.selectableitempicker.ValueBuildBehavior;
import lt.tiem625.docbuild.data.Application;
import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.data.DataAsset;
import lt.tiem625.docbuild.data.Organization;
import lt.tiem625.docbuild.datasource.MetadataProvider;

import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ApplicationsFlowViewController implements Initializable {

    @FXML
    private Label lblSourceDescription;

    @FXML
    private Label lblTargetDescription;

    @FXML
    private Button btnSubmitFlowDetails;

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
        btnSubmitFlowDetails.disableProperty()
                .bind(Bindings.or(
                        Bindings.isNull(sourceApplicationProp),
                        Bindings.isNull(targetApplicationProp))
                );
    }

    @FXML
    private void onPickSourceApplicationClicked() {

        doPickApplicationIntoProp(sourceApplicationProp, "Select SOURCE application");
    }

    @FXML
    private void onPickTargetApplicationClicked() {
        doPickApplicationIntoProp(targetApplicationProp, "Select TARGET application");
    }

    private void doPickApplicationIntoProp(ObjectProperty<BusinessApplication> prop, String pickDialogTitle) {
        ViewWithController<SelectableItemPickerController<Application>> applicationSelectDialog
                = ViewsRepository.getAt(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        applicationSelectDialog.controller().setDialogData(
                businessApplicationPropAsSubtype(prop, Application.class),
                new HashSet<>(metadataProvider.getKnownApplications()),
                ValueBuildBehavior.buildingNotSupported()
        );
        Optional<Application> pickedApplication =
                DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY, pickDialogTitle);
        pickedApplication.ifPresent(prop::set);
    }

    @FXML
    private void onPickSourceAssetClicked() {

        doPickOrBuildDataAssetIntoProp(sourceApplicationProp, "Select SOURCE data asset");
    }

    @FXML
    private void onPickTargetAssetClicked() {

        doPickOrBuildDataAssetIntoProp(targetApplicationProp, "Select TARGET data asset");
    }

    private void doPickOrBuildDataAssetIntoProp(ObjectProperty<BusinessApplication> prop, String pickDialogTitle) {
        ViewWithController<SelectableItemPickerController<DataAsset>> assetSelectDialog
                = ViewsRepository.getAt(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        assetSelectDialog.controller().setDialogData(
                businessApplicationPropAsSubtype(prop, DataAsset.class),
                new HashSet<>(metadataProvider.getKnownDataAssets()),
                new ValueBuildBehavior<>() {
                    @Override
                    public boolean canBuildFromText() {
                        return true;
                    }

                    @Override
                    public DataAsset buildFromText(String text) {

                        ViewWithController<TextAndItemEntityCreateViewController<DataAsset, Organization>> viewWithController =
                                ViewsRepository.getAt(ViewsKeys.DIALOG_CREATE_NEW_ENTITY);
                        viewWithController.controller()
                                .setCreationContext(
                                        "Data asset name:",
                                        "Asset owner organization:",
                                        text, new HashSet<>(metadataProvider.getKnownOrganizations()),
                                        DataAsset::new);
                        Optional<DataAsset> createdAsset =
                                DialogRunner.runValueDialog(ViewsKeys.DIALOG_CREATE_NEW_ENTITY, "Create new data asset");
                        return createdAsset.orElse(null);
                    }
                }
        );

        Optional<DataAsset> pickedDataAsset =
                DialogRunner.runValueDialog(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY, pickDialogTitle);
        pickedDataAsset.ifPresent(prop::set);
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
