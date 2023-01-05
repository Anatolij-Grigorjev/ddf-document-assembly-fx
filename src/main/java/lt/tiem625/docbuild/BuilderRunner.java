package lt.tiem625.docbuild;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lt.tiem625.docbuild.binding.FlowConstruction;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsLoader;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlow;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.datasource.MetadataProvider;
import lt.tiem625.docbuild.datasource.MockMetadataProvider;

import java.io.IOException;

/**
 * JavaFX App
 */
public class BuilderRunner extends Application {

    private final FlowConstruction flowConstruction = new FlowConstruction();

    private final MetadataProvider metadataProvider = new MockMetadataProvider();

    private Stage mainViewStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainViewStage = stage;
        ViewWithController<ApplicationsFlowViewController> applicationsFlowViewParts = ViewsLoader.loadAndStoreViewAtKey(ViewsKeys.SCREEN_APPLICATIONS_FLOW);
        applicationsFlowViewParts.controller().setDataContext(
                null, null, metadataProvider, this::applicationsSelectionDone);
        Scene scene = new Scene(applicationsFlowViewParts.view(), 800, 600);
        mainViewStage.setScene(scene);
        mainViewStage.show();
    }

    private void applicationsSelectionDone(ApplicationsFlow applicationsFlow) {
        System.out.println(applicationsFlow);
    }

    public static void main(String[] args) {
        launch();
    }

}