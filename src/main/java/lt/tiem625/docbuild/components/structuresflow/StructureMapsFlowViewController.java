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

import java.net.URL;
import java.util.ResourceBundle;

public class StructureMapsFlowViewController implements Initializable {

    @FXML
    private Label lblSourceApplication;

    @FXML
    private Label lblTargetApplication;

    @FXML
    private ListView<StructuresMapFlow> lvStructureMaps;
    private final ListProperty<StructuresMapFlow> structureMaps =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    @FXML
    private Button btnRemoveStructuresMapping;

    @FXML
    private Button btnFinalizeFlow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvStructureMaps.setItems(structureMaps);
        btnFinalizeFlow.disableProperty().bind(structureMaps.emptyProperty());
        btnRemoveStructuresMapping.disableProperty()
                .bind(Bindings.or(
                        structureMaps.emptyProperty(),
                        lvStructureMaps.getSelectionModel().selectedItemProperty().isNotNull()
                ));
    }

    @FXML
    private void onFinalizeFlowClicked() {

    }

    @FXML
    private void onRemoveStructuresMappingClicked() {

    }

    @FXML
    private void onAddStructuresMappingClicked() {

    }

    @FXML
    private void onEditApplicationsContextClicked() {

    }
}
