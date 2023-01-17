package lt.tiem625.docbuild;

import javafx.application.Application;
import javafx.stage.Stage;
import lt.tiem625.docbuild.binding.FlowConstruction;
import lt.tiem625.docbuild.binding.FlowConstruction.ApplicationsMappingsContext;
import lt.tiem625.docbuild.components.ViewsKeys;
import lt.tiem625.docbuild.components.ViewsLoader;
import lt.tiem625.docbuild.components.ViewsTransitioner;
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

    private ViewsTransitioner viewsTransitioner;

    @Override
    public void start(Stage stage) throws IOException {
        viewsTransitioner = new ViewsTransitioner(stage);
        //preload views
        cacheFXMLViews();

        viewsTransitioner.<ApplicationsFlowViewController>transitionToViewAtKey(
                ViewsKeys.SCREEN_APPLICATIONS_FLOW,
                controller -> {
                    controller.setDataContext(
                            null, null, knownDataRepository, this::applicationsSelectionDone);
                });

        stage.show();
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

        viewsTransitioner.<StructureMapsFlowViewController>transitionToViewAtKey(
                ViewsKeys.SCREEN_STRUCTURES_MAPPINGS, controller -> {
                    controller.setDataContext(
                            applicationsMappingsContext.source(), applicationsMappingsContext.target(),
                            viewsTransitioner, knownDataRepository,
                            this::applicationsSelectionDone, this::structureMappingsDone
                    );
                });
    }

    private void structureMappingsDone(List<StructuresMapFlow> structuresMappings) {
        System.out.println(structuresMappings);

    }

    public static void main(String[] args) {
        launch();
    }

}