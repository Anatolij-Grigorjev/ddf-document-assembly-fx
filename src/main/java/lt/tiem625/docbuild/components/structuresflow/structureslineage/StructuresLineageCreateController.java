package lt.tiem625.docbuild.components.structuresflow.structureslineage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lt.tiem625.docbuild.components.dialogutils.AbstractPropertyValueDialog;
import lt.tiem625.docbuild.components.structuresflow.StructuresMapFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class StructuresLineageCreateController extends AbstractPropertyValueDialog<StructuresMapFlow> implements Initializable {

    @FXML
    private Label lblSourceApplication;

    @FXML
    private Label lblTargetApplication;

    @FXML
    private Label lblSourceStructure;

    @FXML
    private Label lblNumAttributesSource;

    @FXML
    private Label lblTargetStructure;

    @FXML
    private Label lblNumAttributesTarget;

    @FXML
    private Button btnManageAttrsSource;

    @FXML
    private Button btnManageAttrsTarget;

    @FXML
    private Button btnDone;

    private StructuresMapFlow prevValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onClickSourceStructure() {

    }

    @FXML
    private void onClickFlowService() {

    }

    @FXML
    private void onClickTargetStructure() {

    }

    @FXML
    private void onClickManageAttrsSource() {

    }

    @FXML
    private void onClickManageAttrsTarget() {

    }

    @FXML
    private void onClickDone() {
        throw new UnsupportedOperationException("construct instance");
    }

    @FXML
    private void onClickDiscard() {
        setCurrentFinal(prevValue);
    }
}
