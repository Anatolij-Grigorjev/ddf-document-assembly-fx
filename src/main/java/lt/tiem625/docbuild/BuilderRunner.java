package lt.tiem625.docbuild;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lt.tiem625.docbuild.binding.FlowConstruction;
import lt.tiem625.docbuild.binding.FlowConstruction.ApplicationsMappingsContext;
import lt.tiem625.docbuild.components.ViewWithController;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsLoader;
import lt.tiem625.docbuild.components.ViewsRepository;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlow;
import lt.tiem625.docbuild.components.applicationsflow.ApplicationsFlowViewController;
import lt.tiem625.docbuild.components.structuresflow.StructureMapsFlowViewController;
import lt.tiem625.docbuild.components.structuresflow.StructuresMapFlow;
import lt.tiem625.docbuild.datasource.KnownDataRepository;
import lt.tiem625.docbuild.datasource.MockMetadataDataProvider;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class BuilderRunner extends Application {

    private final FlowConstruction flowConstruction = new FlowConstruction();
    private ApplicationsMappingsContext applicationsMappingsContext;

    private final KnownDataRepository knownDataRepository = new KnownDataRepository(new MockMetadataDataProvider());

    private Stage mainViewStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainViewStage = stage;
        //preload views
        cacheFXMLViews();

        //load applications selection view
        ViewWithController<ApplicationsFlowViewController> applicationsFlowViewParts =
                ViewsRepository.getAt(ViewsKeys.SCREEN_APPLICATIONS_FLOW);
        applicationsFlowViewParts.controller().setDataContext(
                null, null, knownDataRepository, this::applicationsSelectionDone);
        Scene scene = new Scene(applicationsFlowViewParts.view(), 800, 600);
        mainViewStage.setScene(scene);
        mainViewStage.show();
    }

    private void cacheFXMLViews() throws IOException {
        for (ViewsKeys viewKey : ViewsKeys.values()) {
            preloadViewFXML(viewKey);
        }
    }

    private static void preloadViewFXML(ViewsKeys viewKey) throws IOException {
        var viewController = ViewsLoader.loadAndStoreViewAtKey(viewKey);
        System.out.println("view/controller loaded: " + viewController);
    }

    private void applicationsSelectionDone(ApplicationsFlow applicationsFlow) {
        System.out.println(applicationsFlow);
        applicationsMappingsContext =
                flowConstruction.applicationsMappingsConstruction(applicationsFlow.source(), applicationsFlow.target());

        ViewWithController<StructureMapsFlowViewController> structureMapsFlowViewParts =
                ViewsRepository.getAt(ViewsKeys.SCREEN_STRUCTURES_MAPPINGS);
        structureMapsFlowViewParts.controller().setDataContext(
                applicationsMappingsContext.source(), applicationsMappingsContext.target(),
                knownDataRepository, this::structureMappingsDone
        );
        var nextScene = new Scene(structureMapsFlowViewParts.view(), 800, 600);
        mainViewStage.setScene(nextScene);

    }

    private void structureMappingsDone(List<StructuresMapFlow> structuresMappings) {
        System.out.println(structuresMappings);

    }

    public static void main(String[] args) {
        launch();
    }

}