package lt.tiem625.docbuild.components.structuresflow;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsTransitioner;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlow;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.components.dialogutils.Alerts;
import lt.tiem625.docbuild.data.*;
import lt.tiem625.docbuild.datasource.KnownDataRepository;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class StructureMapsFlowViewController implements Initializable {

    @FXML
    private Label lblSourceApplication;

    @FXML
    private Label lblTargetApplication;

    private BusinessApplication sourceApplication;
    private BusinessApplication targetApplication;

    @FXML
    private ListView<StructuresMapFlow> lvStructureMaps;
    private final ListProperty<StructuresMapFlow> structureMaps =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    @FXML
    private Button btnRemoveStructuresMapping;

    @FXML
    private Button btnFinalizeFlow;
    private KnownDataRepository repository;
    private Consumer<List<StructuresMapFlow>> inputFinalizedCallback;
    private ViewsTransitioner viewsTransitioner;
    private Consumer<ApplicationsFlow> applicationsEditedCallback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvStructureMaps.setItems(structureMaps);
        btnFinalizeFlow.disableProperty().bind(structureMaps.emptyProperty());
        btnRemoveStructuresMapping.disableProperty()
                .bind(Bindings.or(
                        structureMaps.emptyProperty(),
                        lvStructureMaps.getSelectionModel().selectedItemProperty().isNull()
                ));
    }

    @FXML
    private void onFinalizeFlowClicked() {

        if (structureMaps.isEmpty()) {
            Alerts.alertErrorOK("Cannot finalize flow without structure maps!");
            return;
        }
        inputFinalizedCallback.accept(structureMaps);
    }

    @FXML
    private void onRemoveStructuresMappingClicked() {

        Optional.ofNullable(lvStructureMaps.getSelectionModel().getSelectedItem())
                .ifPresent(structureMaps::remove);
    }

    private final static AtomicInteger counter = new AtomicInteger(0);
    @FXML
    private void onAddStructuresMappingClicked() {

        structureMaps.add(new StructuresMapFlow(
                new Structure(sourceApplication, "source-" + counter.incrementAndGet(), new StructureType("SQL Table")),
                new Service(targetApplication, "unknown-" + counter.incrementAndGet(), new IntegrationType("API")),
                new Structure(targetApplication, "target-" + counter.incrementAndGet(), new StructureType("SQL Table")))
        );
    }

    @FXML
    private void onEditApplicationsContextClicked() {
        var answer = Alerts.alertPromptOKCancel("Are you sure you wish to change the applications context? " +
                "This will discard all created structure mappings.");

        switch (answer) {

            case IGNORED, CANCEL -> {
                return;
            }
            case PROCEED -> {
                viewsTransitioner.<ApplicationsFlowViewController>transitionToViewAtKey(
                        ViewsKeys.SCREEN_APPLICATIONS_FLOW,
                        controller -> controller
                                .setDataContext(
                                        sourceApplication, targetApplication,
                                        repository, applicationsEditedCallback)
                );
            }
        }
    }

    public void setDataContext(
            BusinessApplication sourceApplication,
            BusinessApplication targetApplication,
            ViewsTransitioner viewsTransitioner,
            KnownDataRepository repository,
            Consumer<ApplicationsFlow> applicationsEditedCallback,
            Consumer<List<StructuresMapFlow>> inputFinalizedCallback) {

        Objects.requireNonNull(sourceApplication);
        Objects.requireNonNull(targetApplication);
        Objects.requireNonNull(viewsTransitioner);
        Objects.requireNonNull(repository);
        Objects.requireNonNull(applicationsEditedCallback);
        Objects.requireNonNull(inputFinalizedCallback);

        this.sourceApplication = sourceApplication;
        this.targetApplication = targetApplication;
        lblSourceApplication.setText(sourceApplication.asView());
        lblTargetApplication.setText(targetApplication.asView());
        structureMaps.clear();

        this.viewsTransitioner = viewsTransitioner;
        this.repository = repository;
        this.applicationsEditedCallback = applicationsEditedCallback;
        this.inputFinalizedCallback = inputFinalizedCallback;
    }
}
