package lt.tiem625.docbuild.components.applicationsflow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationsFlowViewController implements Initializable {

    @FXML
    private Button btnSourceApplication;

    @FXML
    private Button btnSourceDataAsset;

    @FXML
    private Button btnTargetApplication;

    @FXML
    private Button btnTargetDataAsset;

    @FXML
    private Label lblSourceDescription;

    @FXML
    private Label lblTargetDescription;

    @FXML
    private Button btnFlowDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
