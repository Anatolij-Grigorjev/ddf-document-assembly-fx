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
import lt.tiem625.docbuild.datasource.KnownDataRepository;
import lt.tiem625.docbuild.datasource.MockMetadataDataProvider;

import java.io.IOException;

/**
 * JavaFX App
 */
public class BuilderRunner extends Application {

    private final FlowConstruction flowConstruction = new FlowConstruction();

    private final KnownDataRepository knownDataRepository = new KnownDataRepository(new MockMetadataDataProvider());

    private Stage mainViewStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainViewStage = stage;
        //load helper dialog views
        preloadDialogFXMLAtViewKey(ViewsKeys.DIALOG_SELECT_KNOWN_ENTITY);
        preloadDialogFXMLAtViewKey(ViewsKeys.DIALOG_CREATE_NEW_ENTITY);

        //load applications selection view
        ViewWithController<ApplicationsFlowViewController> applicationsFlowViewParts = ViewsLoader.loadAndStoreViewAtKey(ViewsKeys.SCREEN_APPLICATIONS_FLOW);
        applicationsFlowViewParts.controller().setDataContext(
                null, null, knownDataRepository, this::applicationsSelectionDone);
        Scene scene = new Scene(applicationsFlowViewParts.view(), 800, 600);
        mainViewStage.setScene(scene);
        mainViewStage.show();
    }

    private static void preloadDialogFXMLAtViewKey(ViewsKeys viewKey) throws IOException {
        var dialogViewController = ViewsLoader.loadAndStoreViewAtKey(viewKey);
        System.out.println("Dialog view/controller loaded: " + dialogViewController);
    }

    private void applicationsSelectionDone(ApplicationsFlow applicationsFlow) {
        System.out.println(applicationsFlow);
    }

    public static void main(String[] args) {
        launch();
    }

}