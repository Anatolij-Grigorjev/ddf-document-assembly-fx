package lt.tiem625.docbuild.components.applicationsflow;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lt.tiem625.docbuild.data.BusinessApplication;
import lt.tiem625.docbuild.datasource.MetadataProvider;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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

    public void setDataContext(
            BusinessApplication currentSource,
            BusinessApplication currentTarget,
            MetadataProvider metadataProvider,
            Consumer<ApplicationsFlow> selectionDoneCallback
    ) {

    }
}
